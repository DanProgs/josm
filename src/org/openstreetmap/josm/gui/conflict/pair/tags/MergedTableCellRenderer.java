// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.gui.conflict.pair.tags;

import static org.openstreetmap.josm.tools.I18n.tr;

import javax.swing.table.TableCellRenderer;

import org.openstreetmap.josm.gui.conflict.ConflictColors;
import org.openstreetmap.josm.gui.conflict.pair.MergeDecisionType;

/**
 * This {@link TableCellRenderer} displays the decision a user made regarding a tag conflict
 */
public class MergedTableCellRenderer extends TagMergeTableCellRenderer {
    protected void setBackgroundColor(TagMergeItem item, boolean isSelected) {
        if (isSelected) {
            setBackground(ConflictColors.BGCOLOR_SELECTED.get());
            return;
        }
        final MergeDecisionType decision = item != null ? item.getMergeDecision() : null;
        if (decision == MergeDecisionType.KEEP_MINE || decision == MergeDecisionType.KEEP_THEIR) {
            setBackground(ConflictColors.BGCOLOR_COMBINED.get());
        } else {
            setBackground(ConflictColors.BGCOLOR_UNDECIDED.get());
        }
    }

    @Override
    protected void renderKey(TagMergeItem item, boolean isSelected) {
        renderMergedCell(item, isSelected, true);
    }

    @Override
    protected void renderValue(TagMergeItem item, boolean isSelected) {
        renderMergedCell(item, isSelected, false);
    }

    private void renderMergedCell(TagMergeItem item, boolean isSelected, boolean showKey) {
        setBackgroundColor(item, isSelected);

        final MergeDecisionType decision = item.getMergeDecision();
        if (decision == null || decision == MergeDecisionType.UNDECIDED) {
            setText("");
            setToolTipText(null);
            return;
        }

        final String chosenValue = switch (decision) {
            case KEEP_MINE -> item.getMyTagValue();
            case KEEP_THEIR -> item.getTheirTagValue();
            default -> null;
        };

        if (chosenValue == null) {
            setText(tr("<undefined>"));
            setToolTipText(tr("The merged dataset will not include a tag with key {0}", item.getKey()));
        } else {
            final String text = showKey ? item.getKey() : chosenValue;
            setText(text);
            setToolTipText(text);
        }
    }
}
