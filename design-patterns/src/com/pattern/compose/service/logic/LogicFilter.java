package com.pattern.compose.service.logic;

import com.pattern.compose.model.vo.TreeNodeLink;

import java.util.List;
import java.util.Map;

/**
 * @author chenjingyi
 */
public interface LogicFilter {

    /**
     * 逻辑决策器
     *
     * @param matterValue       决策值
     * @param treeNodeLinkList  决策节点
     * @return  下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLink> treeNodeLinkList);

    /**
     * 获取决策值
     *
     * @param treeId
     * @param userId
     * @param decisionMatter    决策物料
     * @return  决策值
     */
    String matterValue(Long treeId, String userId, Map<String, String> decisionMatter);
}
