package net.jalmus;

import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

//--------------------------------------------------------------------
/** (description of class)
 * @author Created by Louis Thomas on Sep 26, 2008. */
public interface Localizable {
    void update(ResourceBundle resourceBundle);


    //----------------------------------------------------------------
    class Button implements Localizable {
        private final AbstractButton m_button;
        private final String m_sKey;

        public Button(AbstractButton button, String sKey) {
            m_button=button;
            m_sKey=sKey;
        }

        public void update(ResourceBundle resourceBundle) {
            m_button.setText(resourceBundle.getString(m_sKey));
        }
    }

    //----------------------------------------------------------------
    class Dialog implements Localizable {
        private final java.awt.Dialog m_dialog;
        private final String m_sKey;

        public Dialog(java.awt.Dialog dialog, String sKey) {
            m_dialog=dialog;
            m_sKey=sKey;
        }

        public void update(ResourceBundle resourceBundle) {
            m_dialog.setTitle(resourceBundle.getString(m_sKey));
        }
    }

    //----------------------------------------------------------------
    class Tab implements Localizable {
        private final JTabbedPane m_tabbedPane;
        private final int m_nTabIndex;
        private final String m_sKey;

        public Tab(JTabbedPane tabbedPane, int nTabIndex, String sKey) {
            m_tabbedPane=tabbedPane;
            m_nTabIndex=nTabIndex;
            m_sKey=sKey;
        }

        public void update(ResourceBundle resourceBundle) {
            m_tabbedPane.setTitleAt(m_nTabIndex, resourceBundle.getString(m_sKey));
        }
    }

    //----------------------------------------------------------------
    class NamedGroup implements Localizable {
        private final JComponent m_component;
        private final String m_sKey;

        public NamedGroup(JComponent component, String sKey) {
            m_component=component;
            m_sKey=sKey;
        }

        public void update(ResourceBundle resourceBundle) {
            Border titledBorder=BorderFactory.createTitledBorder(resourceBundle.getString(m_sKey));
            Border emptyBorder=BorderFactory.createEmptyBorder(5, 5, 5, 5);
            m_component.setBorder(BorderFactory.createCompoundBorder(titledBorder, emptyBorder));

        }
    }
}
