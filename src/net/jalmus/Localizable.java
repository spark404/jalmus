package net.jalmus;

import java.util.ResourceBundle;
import javax.swing.AbstractButton;

//--------------------------------------------------------------------
/** (description of class)
 * @author Created by Louis Thomas on Sep 26, 2008. */
public interface Localizable {
    void update(ResourceBundle resourceBundle);


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

}
