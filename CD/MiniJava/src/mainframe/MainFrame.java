package mainframe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import minijavaparser.*;
import progen.*;

public class MainFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JSplitPane m_splitPaneMain;
	private JSplitPane m_splitPaneAST;
	private JButton m_buttonTranslate;
	private JScrollPane m_scrollPaneASTLeft;
	private JScrollPane m_scrollPaneASTRight;
	private JScrollPane m_scrollPaneMainRight;
	private JTree m_treeAST;
	private JTextArea m_textAreaTokens;
	private JTextArea m_textAreaFacts;

	private ProgenNode m_rootNode;

	public MainFrame(ProgenNode root) 
	{
		super("AST Viewer");
		m_rootNode = root;
		initMainFrame();
	}

	private void initMainFrame() 
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setSize(dimension.width * 99 / 100, dimension.height * 85 / 100);
		setLocation(dimension.width * 1 / 200, dimension.height * 15 / 200);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// AST
		m_treeAST = new JTree(new DefaultTreeModel(m_rootNode));
		m_treeAST.putClientProperty("JTree.lineStyle", "Angled");
		for (int nIndex = 0; nIndex < m_treeAST.getRowCount(); nIndex++) 
		{
			m_treeAST.expandRow(nIndex);
		}
		m_treeAST.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_treeAST.addTreeSelectionListener(new TreeSelectionListener()
		{
				public void valueChanged(TreeSelectionEvent e)
				{
					ProgenNode node = 
						(ProgenNode)m_treeAST.getLastSelectedPathComponent();
					if (node != null)
					{
						String szText = "";
						Token t = node.jjtGetFirstToken();
						if(t != null)
						{
							do 
							{
								szText+="  "+t.image+"\n";
								if(t == node.jjtGetLastToken())
								{
									break;
								}
								t = t.next;
						    }
							while (t != null);
						}
						m_textAreaTokens.setText(szText);
					}
				}
		});

		m_scrollPaneASTLeft = new JScrollPane(m_treeAST);
		m_scrollPaneASTLeft.setPreferredSize(new Dimension(this.getWidth() * 32/100,
				this.getHeight()));

		// tokens
		m_textAreaTokens = new JTextArea();
		m_textAreaTokens.setEditable(false);
		m_scrollPaneASTRight = new JScrollPane(m_textAreaTokens);
		m_scrollPaneASTRight.setPreferredSize(new Dimension(this.getWidth() * 32/100,
				this.getHeight()));

		
		// tree + tokens
		m_splitPaneAST = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				m_scrollPaneASTLeft, m_scrollPaneASTRight);

		// facts
		m_textAreaFacts = new JTextArea();
		m_textAreaFacts.setEditable(false);
		m_scrollPaneMainRight = new JScrollPane(m_textAreaFacts);
		m_scrollPaneMainRight.setPreferredSize(new Dimension(this.getWidth() * 32/100,
				this.getHeight()));
		m_splitPaneMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				m_splitPaneAST, m_scrollPaneMainRight);
				
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent windowEvent) 
			{
				menuJavaXProjectExit();
			}
		});

		m_buttonTranslate = new JButton("About");
		m_buttonTranslate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
			}
		});

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(m_splitPaneMain, BorderLayout.CENTER);
		getContentPane().add(m_buttonTranslate, BorderLayout.SOUTH);
	}


	public void menuJavaXProjectExit() 
	{
		System.exit(0);
	}

	public void menuAboutAboutTheAuthor() 
	{
		AboutTheAuthorDialog aboutTheAuthorDialog = 
			new AboutTheAuthorDialog(this);
		aboutTheAuthorDialog.setVisible(true);
	}
}
