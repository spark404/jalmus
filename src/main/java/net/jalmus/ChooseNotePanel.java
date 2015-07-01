package net.jalmus;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;



public class ChooseNotePanel extends JPanel {
    private boolean DEBUG = false;
    private JTable table;
    JButton okButton;
    
    private static int NOTEREADING = 1;
    private static int RHYTHMREADING = 2;
    private static int SCOREREADING = 3;
    
    public ChooseNotePanel(String key, int leveltype , ResourceBundle bundle) {
    	System.out.println(key+leveltype);
    	
    	if (leveltype == NOTEREADING && key == "treble")     	 table = new JTable(new TableKeyTrebble());
    	else if (leveltype == NOTEREADING && key == "bass")     	 table = new JTable(new TableKeyBass());
     	else if (leveltype == NOTEREADING && key == "both")     	table = new JTable(new NotesTableModel());
    	else if (leveltype == SCOREREADING && key == "treble")     	 table = new JTable(new TableKeyScoreTrebble());
    	else if (leveltype == SCOREREADING && key == "bass")     	 table = new JTable(new TableKeyScoreBass());
    	else table = new JTable(new NotesTableModel());
    	 
        table.setPreferredScrollableViewportSize(new Dimension(610, 115));
        table.setFillsViewportHeight(false);

        
       for (int vColIndex = 1; vColIndex < 13; vColIndex++) {
       TableColumn col = table.getColumnModel().getColumn(vColIndex);
      col.setCellRenderer(new CheckBoxTableCellRenderer());
       }
       
       TableColumn column = table.getColumnModel().getColumn(0);
       column.setPreferredWidth(110);
   	
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        okButton=new JButton();
       okButton.setText(bundle.getString("_buttonok"));
       okButton.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));

        JButton clearButton=new JButton();
        clearButton.setText(bundle.getString("_buttonclear"));
       clearButton.setIcon(new ImageIcon(getClass().getResource("/images/eraser.png")));
        

  /*      okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 if (!atLeast3Pitches()) JOptionPane.showMessageDialog(null, "Choose at least three notes", "Warning", JOptionPane.ERROR_MESSAGE); 
            	//System.out.println(getFocusCycleRootAncestor());
            	 else getFocusCycleRootAncestor().setVisible(false);
            }
        });*/
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             resettable();
            }
        });
        
  
        //Add the scroll pane to this panel  
   //    setLayout(new BorderLayout());
        add(scrollPane);
        add(okButton);
        add(clearButton);
    }
 


        

        
    public  JTable gettable(){
    	return this.table;
    }
    
    public  JButton getButton(){
    	return this.okButton;
    }
    
    public ArrayList<Integer> getPitches(){
        ArrayList<Integer> pitchselected = new ArrayList<Integer>(); 
        
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        for (int i = 0; i < numRows; i++){
        	for (int j = 1; j < numCols; j++){
          		if ((Boolean) table.getValueAt(i, j)) pitchselected.add(24+12*i+(j-1)); // first note Octava -3 C pitch 24
        	}
        }     
        return pitchselected;
    }
    
    
    public void updateTable(ArrayList<Integer> pitcheslist){
    	int tmp = 0;
    	for (Integer pitch : pitcheslist) {
    		//to do
    		  int numRows = table.getRowCount();
    	        int numCols = table.getColumnCount();
    	        for (int i = 0; i < numRows; i++){
    	        	for (int j = 1; j < numCols; j++){
    	        		tmp = 24+12*i+(j-1);
    	          		if (tmp == pitch) {
    	          			table.setValueAt(true,i, j);
    	          			//System.out.println("i: "+i+" j: "+j );
    	          		}
    	        	}
    	        }     
    		
    		}
 
    }
    
    public boolean  atLeast3Pitches(){
    	int nbpitches = 0;
    	  int numRows = table.getRowCount();
          int numCols = table.getColumnCount();
          for (int i = 0; i < numRows; i++){
          	for (int j = 1; j < numCols; j++){
          		if ( (Boolean) table.getValueAt(i, j)) nbpitches++;
          		 	}
          }     
    	 return (nbpitches >= 3);
    }
    

    
    public void resettable(){
    	
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        for (int i = 0; i < numRows; i++){
        	for (int j = 1; j < numCols; j++){
        		 table.setValueAt(false,i, j);
        	}
    }
    }
    
    
    
    //To modify checkbox background when not editable
    
    class CheckBoxTableCellRenderer
    extends JCheckBox
    implements TableCellRenderer {
    
    Border noFocusBorder;
    Border focusBorder;

    public CheckBoxTableCellRenderer() {
      super();
      //setOpaque(true);
      setContentAreaFilled(true);  // use this instead of setOpaque()
      setBorderPainted(true);
      setHorizontalAlignment(SwingConstants.CENTER);
      setVerticalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column) {

    	if(table == null) {
        // ???
      }  
      else {
        if (isSelected) {
          setForeground(table.getSelectionForeground());
          setBackground(table.getSelectionBackground());
          if (! table.isCellEditable(row, column))this.setBackground(Color.BLACK);
        }
        else {
          setForeground(table.getForeground());
          setBackground(table.getBackground());
          if (! table.isCellEditable(row, column))this.setBackground(Color.BLACK);
      
        }
        setEnabled(table.isEnabled());
        setComponentOrientation(table.getComponentOrientation());

        if (hasFocus) {
          if (focusBorder == null) {
            focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
          }
          setBorder(focusBorder);
        }
        else {
          if (noFocusBorder == null) {
            if (focusBorder == null) {
              focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            if (focusBorder != null) {
              Insets n = focusBorder.getBorderInsets(this);
              noFocusBorder = new EmptyBorder(n);
            }
          }
          setBorder(noFocusBorder);
        }

        setSelected(Boolean.TRUE.equals(value));
      }
      return this;
    }
   }
    
    
    
    
    
    // Table Model generic 
    class NotesTableModel extends AbstractTableModel {


        private String[] columnNames = {"Octava", "C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"};
        private Object[][] data = {
        		{"Octava-3",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava-2",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava-1",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava 0",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava 1",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava 2",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)},
        		{"Octava 3",new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false), new Boolean(false), new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false),new Boolean(false)}    		
        };
 
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
            return data.length;
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            // pitch 26 D-3 to 96 C+3 
        	
            int numCols = getColumnCount();
            
            if (
            	(row == 0 & col < 3 )
               || (row == 6 & col >  1)
            ) 
            {
                return false;
            }          
            else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }
 
            data[row][col] = value;
            fireTableCellUpdated(row, col);
 
            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
    
        }
 
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();
 
            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
 

    class TableKeyTrebble extends NotesTableModel {
    	
    	
    	  public boolean isCellEditable(int row, int col) {
    		  
    		  int numRows = getRowCount();
              int numCols = getColumnCount();
              
              //Note that the data/cell address is constant,
              //no matter where the cell appears onscreen.
              // pitch 47 b-2 to 96 c+3
              if ((col < 1)  //col indicate the octava
               || (row == 0 & col < numCols )
               || (row == 1 & col < numCols-1 )
               || (row == 6 & col >  1)
              )  
              {
                  return false;
              }          
              else {
                  return true;
              }
          }
    }
    
    class TableKeyBass extends NotesTableModel {
  	  public boolean isCellEditable(int row, int col) {
  		int numRows = getRowCount();
        int numCols = getColumnCount();
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
        //pitch 26 D-3 to 74 D+1
            if ((col < 1)  //col indicate the octava
             || (row == 0 & col < 3 )
             || (row == 4 & col > 3 )
             || (row == 5 & col < numCols )
             || (row == 6 & col < numCols )
            )  //pitch 24 25 not supported
            {
                return false;
            }          
            else {
                return true;
            }
        }
  }
    
    class TableKeyScoreTrebble extends NotesTableModel {
    	
    	
  	  public boolean isCellEditable(int row, int col) {
  		  
  		  int numRows = getRowCount();
            int numCols = getColumnCount();
            
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            // pitch c0 60 c2 84
            if ((col < 1) //col indicate the octava
             || (row == 0 ) || (row == 1) 
             || (row == 2 & col <  8)
             || (row == 5 & col >  1)
             || (row == 6) 
            )  
            {
                return false;
            }          
            else {
                return true;
            }
        }
  }
    
    class TableKeyScoreBass extends NotesTableModel {
    	  public boolean isCellEditable(int row, int col) {
    		int numRows = getRowCount();
          int numCols = getColumnCount();
              //Note that the data/cell address is constant,
              //no matter where the cell appears onscreen.
          //pitch 26 D-3 to 74 D+1
              if ((col < 1)  //col indicate the octava
               || (row == 0 & col < 12)
               || (row == 3 & col > 5 )
                || (row == 4 & col < numCols )
               || (row == 5 & col < numCols )
               || (row == 6 & col < numCols )
              )  //pitch 24 25 not supported
              {
                  return false;
              }          
              else {
                  return true;
              }
          }
    }
  

}
