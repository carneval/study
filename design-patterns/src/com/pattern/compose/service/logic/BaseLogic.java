package com.pattern.compose.service.logic;

import com.pattern.compose.model.vo.TreeNodeLink;

import java.util.List;
import java.util.Map;

/**
 * @author chenjingyi
 */
public abstract class BaseLogic implements LogicFilter {
    
    @Override
    public Long filter(String matterValue, List<TreeNodeLink> treeNodeLinkList) {
        for (TreeNodeLink nodeLink : treeNodeLinkList) {
            if (decisionLogic(matterValue, nodeLink)) {
                return nodeLink.getNodeIdTo();
            }
        }
        return null;
    }

    @Override
    public abstract String matterValue(Long treeId, String userId, Map<String, String> decisionMatter);

    private boolean decisionLogic(String matterValue, TreeNodeLink nodeLink) {
        String limit = nodeLink.getRuleLimitValue();
        switch (nodeLink.getRuleLimitType()) {
            case 1:
                return matterValue.equals(limit);
            case 2:
                return Double.parseDouble(matterValue) > Double.parseDouble(limit);
            case 3:
                return Double.parseDouble(matterValue) < Double.parseDouble(limit);
            case 4:
                return Double.parseDouble(matterValue) >= Double.parseDouble(limit);
            case 5:
                return Double.parseDouble(matterValue) <= Double.parseDouble(limit);
            default:
                return false;
        }

    }
}
