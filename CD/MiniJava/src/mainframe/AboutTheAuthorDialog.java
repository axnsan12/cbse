package mainframe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * The about the author dialog is a simple modal dialog box which
 * displays information about the people who wrote this utility.
 */
public class AboutTheAuthorDialog extends JDialog
{
  private JPanel m_panelAbout=null;
  private JLabel m_labelLines[]=null;
  private int m_nNoOfLines=11;

  private JPanel m_panelConfirmation=null;
  private JButton m_buttonOk=null;
  private JButton m_buttonCancel=null;

  public AboutTheAuthorDialog(JFrame frame)
  {
    super(frame,"About the author",true);

    // About side
    m_panelAbout=new JPanel();
    m_panelAbout.setLayout(new GridLayout(m_nNoOfLines,1,5,5));
    m_panelAbout.setBorder(BorderFactory.createEtchedBorder());
    m_panelAbout.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    m_labelLines=new JLabel[m_nNoOfLines];
    for(int nIndex=0;nIndex<m_nNoOfLines;nIndex++)
    {
      m_labelLines[nIndex]=new JLabel("");
    }
    m_labelLines[0].setText("Developed by:");
    m_labelLines[1].setText("asist.dr.ing. Ciprian-Bogdan Chirila");
    m_labelLines[2].setText("E-mail: chirila@cs.upt.ro");
    m_labelLines[3].setText("asist.dr.ing. Calin Jebeleanu");
    m_labelLines[4].setText("E-mail: calin@cs.upt.ro");
    m_labelLines[5].setText("Copyright (c) 2010-2011");
    m_labelLines[6].setText("All rights reserved");
    m_labelLines[7].setText("");
    m_labelLines[8].setText("");    
    m_labelLines[9].setText("");
    m_labelLines[10].setText("");

    for(int nIndex=0;nIndex<m_nNoOfLines;nIndex++)
    {
      m_labelLines[nIndex].setHorizontalAlignment(JLabel.CENTER);
      m_panelAbout.add(m_labelLines[nIndex]);
    }

    // Confirmation side
    m_panelConfirmation=new JPanel();
    m_panelConfirmation.setLayout(new GridLayout(1,2,10,0));
    m_panelConfirmation.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    m_buttonOk=new JButton("Ok",
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonDefaultOk.gif")));
    m_buttonOk.setPressedIcon(
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonDownOk.gif")));
    m_buttonOk.setRolloverEnabled(true);
    m_buttonOk.setRolloverIcon(
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonRolloverOk.gif")));
    m_buttonOk.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false);
      }
    });
    m_buttonCancel=new JButton("Cancel",
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonDefaultCancel.gif")));
    m_buttonCancel.setPressedIcon(
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonDownCancel.gif")));
    m_buttonCancel.setRolloverEnabled(true);
    m_buttonCancel.setRolloverIcon(
            new ImageIcon(this.getClass().getClassLoader().getResource("images/buttonRolloverCancel.gif")));
    m_buttonCancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false);
      }
    });
    m_panelConfirmation.add(m_buttonOk);
    m_panelConfirmation.add(m_buttonCancel);

    JPanel contentPane=new JPanel();
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout());
    contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    contentPane.add(m_panelAbout,BorderLayout.CENTER);
    contentPane.add(m_panelConfirmation,BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(frame);
  }
}
