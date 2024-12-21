package com.xjsun.flow.config;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

@LiteflowComponent("c")
public class CCmp extends NodeComponent {

	@Override
	public void process() {
		System.out.println("CCmp executed!");
	}
}