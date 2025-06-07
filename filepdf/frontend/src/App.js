import React, { useRef, useState } from 'react';
import { Column } from '@ant-design/plots';
import { Button, Modal } from 'antd';
import html2canvas from 'html2canvas';
import 'antd/dist/reset.css';

const App = () => {
  const pageContainerRef = useRef(null); // Ref for the entire page container
  const [pdfUrl, setPdfUrl] = useState('');
  const [isPreviewModalVisible, setIsPreviewModalVisible] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const mockData = [
    { '月份': '1月', '月均降雨量': 100, name: '北京' },
    { '月份': '2月', '月均降雨量': 120, name: '北京' },
    { '月份': '3月', '月均降雨量': 140, name: '北京' },
    { '月份': '1月', '月均降雨量': 80, name: '上海' },
    { '月份': '2月', '月均降雨量': 90, name: '上海' },
    { '月份': '3月', '月均降雨量': 110, name: '上海' },
  ];

  const config = {
    data: mockData,
    xField: '月份',
    yField: '月均降雨量',
    colorField: 'name',
    group: true,
    style: {
      inset: 5,
    },
  };

  const handleGeneratePdf = async () => {
    if (!pageContainerRef.current) {
      console.error('Page container is not available');
      return;
    }
    setIsLoading(true);

    try {
      // 1. Use html2canvas to capture the page container
      const canvas = await html2canvas(pageContainerRef.current);
      const imgDataUrl = canvas.toDataURL('image/png');

      // 2. Create a simple HTML structure with the image
      const htmlContent = `
        <html>
          <body style="margin: 0;">
            <img src="${imgDataUrl}" style="max-width: 100%; display: block;" />
          </body>
        </html>
      `;
      
      // 3. Send this simple HTML to the backend
      const response = await fetch('/api/generate-pdf', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ htmlContent }),
      });

      if (response.ok) {
        const blob = await response.blob();
        const url = URL.createObjectURL(blob);
        setPdfUrl(url);
        setIsPreviewModalVisible(true);
      } else {
        console.error('Failed to generate PDF');
      }
    } catch (error) {
      console.error('Error generating PDF:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDownload = () => {
    const a = document.createElement('a');
    a.href = pdfUrl;
    a.download = 'rainfall-chart.pdf';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  };
  
  const handleCancel = () => {
    setIsPreviewModalVisible(false);
    if(pdfUrl) {
      URL.revokeObjectURL(pdfUrl);
      setPdfUrl('');
    }
  };

  return (
    <div style={{ padding: '20px' }} ref={pageContainerRef}>
      <h1>降雨数据图表</h1>
      <p>这是一份关于降雨量的月度数据统计图表。</p>
      <Column {...config} />
      <Button type="primary" onClick={handleGeneratePdf} loading={isLoading} style={{ marginTop: '20px' }}>
        {isLoading ? '正在生成...' : '生成 PDF'}
      </Button>

      <Modal
        title="PDF 预览"
        open={isPreviewModalVisible}
        onOk={handleDownload}
        onCancel={handleCancel}
        width="80%"
        okText="下载"
        cancelText="关闭"
        style={{ top: 20 }}
      >
        {pdfUrl && (
          <iframe
            src={pdfUrl}
            style={{ width: '100%', height: '75vh', border: 'none' }}
            title="PDF Preview"
          ></iframe>
        )}
      </Modal>
    </div>
  );
};

export default App;
