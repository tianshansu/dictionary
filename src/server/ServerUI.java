package server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import entities.DictWord;
import services.DictionaryService;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

/**
 * Server UI
 */
public class ServerUI {
	private DictionaryService dictionaryService;
	private JFrame frmServer;
	private JPanel panelParent;
	private JTextField textFieldCurrentUserCount;
	private JPanel panelUserCount;
	private JTable tableWords;
	private JPanel panelWords;
	private JTextField textFieldCurrentWordsCount;
	private JToggleButton tglbtnUsers;
	private JPanel panelUsers;
	private JScrollPane scrollPaneUsers;
	private JTable tableUsers;

	
	/**
	 * get current frame (to display warning message above)
	 * @return Jframe
	 */
	public JFrame getFrame() {
        return frmServer;
    }
	
	
	
	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmServer.setVisible(true);
				} catch (Exception e) {
					System.out.println("Server UI cannot be loaded!");
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerUI(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.getContentPane().setBackground(Color.WHITE);
		frmServer.setType(Type.UTILITY);
		frmServer.setTitle("Client");
		frmServer.setBackground(Color.WHITE);
		frmServer.setBounds(100, 100, 924, 684);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblServer = new JLabel("Server");
		lblServer.setFont(new Font("Arial", Font.PLAIN, 30));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOpaque(false);
		toolBar.setBackground(null);
		toolBar.setBorderPainted(false);

		// search word button
		JToggleButton tglbtnOverview = new JToggleButton("Overview");
		tglbtnOverview.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnOverview.setSelected(true);
		tglbtnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overviewLayout();
			}
		});
		cleanButtonLayout(tglbtnOverview);
		toolBar.add(tglbtnOverview);

		// add word button
		JToggleButton tglbtnDictionary = new JToggleButton("Dictionary");
		tglbtnDictionary.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnDictionary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dictionaryLayout();
			}
		});
		cleanButtonLayout(tglbtnDictionary);
		toolBar.add(tglbtnDictionary);
		
		
		tglbtnUsers = new JToggleButton("Users");
		tglbtnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usersLayout();
			}
		});
		tglbtnUsers.setFont(new Font("Calibri", Font.BOLD, 12));
		cleanButtonLayout(tglbtnUsers);
		toolBar.add(tglbtnUsers);

		ButtonGroup group = new ButtonGroup();
		group.add(tglbtnOverview);
		group.add(tglbtnDictionary);
		group.add(tglbtnUsers);

		panelParent = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmServer.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelParent, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(401)
					.addComponent(lblServer, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(390, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(373)
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(338, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblServer, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panelParent, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
		);
		
		
		panelParent.setLayout(new BoxLayout(panelParent, BoxLayout.Y_AXIS));

		panelUserCount = new JPanel();
		panelUserCount.setBackground(new Color(255, 255, 255));
		panelParent.add(panelUserCount);

		textFieldCurrentUserCount = new JTextField();
		textFieldCurrentUserCount.setBackground(new Color(255, 255, 255));
		textFieldCurrentUserCount.setEditable(false);
		textFieldCurrentUserCount.setColumns(10);
		textFieldCurrentUserCount.setText("0");
		textFieldCurrentUserCount.setBorder(null);

		JLabel lblCurrentUserCount = new JLabel("Current User Count:");
		lblCurrentUserCount.setFont(new Font("Calibri", Font.BOLD, 20));
		
		JLabel lblCurrentWordsCount = new JLabel("Current Words Count:");
		lblCurrentWordsCount.setFont(new Font("Calibri", Font.BOLD, 20));
		
		textFieldCurrentWordsCount = new JTextField();
		textFieldCurrentWordsCount.setBackground(new Color(255, 255, 255));
		textFieldCurrentWordsCount.setText("0");
		textFieldCurrentWordsCount.setEditable(false);
		textFieldCurrentWordsCount.setColumns(10);
		textFieldCurrentWordsCount.setBorder(null);
		
		
		
		GroupLayout gl_panelUserCount = new GroupLayout(panelUserCount);
		gl_panelUserCount.setHorizontalGroup(
			gl_panelUserCount.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelUserCount.createSequentialGroup()
					.addGap(300)
					.addGroup(gl_panelUserCount.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCurrentUserCount, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCurrentWordsCount, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelUserCount.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldCurrentUserCount, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldCurrentWordsCount, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(310, Short.MAX_VALUE))
		);
		gl_panelUserCount.setVerticalGroup(
			gl_panelUserCount.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUserCount.createSequentialGroup()
					.addGroup(gl_panelUserCount.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelUserCount.createSequentialGroup()
							.addGap(15)
							.addComponent(lblCurrentUserCount, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblCurrentWordsCount, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelUserCount.createSequentialGroup()
							.addContainerGap()
							.addComponent(textFieldCurrentUserCount, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldCurrentWordsCount, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelUserCount.setLayout(gl_panelUserCount);

		panelWords = new JPanel();
		panelWords.setBackground(new Color(255, 255, 255));
		panelParent.add(panelWords);
		
		
		tableWords = new JTable();
		tableWords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//when click the delete text, call service and delete it
				int row = tableWords.rowAtPoint(e.getPoint());
		        int column = tableWords.columnAtPoint(e.getPoint());
		        
		        //only do the action if the client clicked operation column
		        if (column == 1) {
		            String wordToDelete = (String) tableWords.getValueAt(row, 0);
		            System.out.println("Server clicked delete for: " + wordToDelete);
		            	
		            DictWord word=new DictWord();
		            word.setWord(wordToDelete);
		            dictionaryService.deleteWord(word);

		            refreshWords();
		        }
			}
		});

	

		JScrollPane scrollPaneWords = new JScrollPane(tableWords);
		
		scrollPaneWords.setBackground(Color.WHITE);
		scrollPaneWords.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPaneWords.getViewport().setBackground(Color.WHITE);
		tableWords.getTableHeader().setBackground(Color.WHITE);
		
		
		GroupLayout gl_panelWords = new GroupLayout(panelWords);
		gl_panelWords.setHorizontalGroup(
			gl_panelWords.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWords.createSequentialGroup()
					.addGap(100)
					.addComponent(scrollPaneWords, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(100, Short.MAX_VALUE))
		);
		gl_panelWords.setVerticalGroup(
			gl_panelWords.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWords.createSequentialGroup()
					.addGap(25)
					.addComponent(scrollPaneWords, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		panelWords.setLayout(gl_panelWords);
		
		refreshWords();
		
		
		panelUsers = new JPanel();
		panelUsers.setBackground(Color.WHITE);
		panelParent.add(panelUsers);
		
		scrollPaneUsers = new JScrollPane((Component) null);
		
		scrollPaneUsers.setBackground(Color.WHITE);
		scrollPaneUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPaneUsers.getViewport().setBackground(Color.WHITE);
		
		
		
		GroupLayout gl_panelUsers = new GroupLayout(panelUsers);
		gl_panelUsers.setHorizontalGroup(
			gl_panelUsers.createParallelGroup(Alignment.LEADING)
				.addGap(0, 900, Short.MAX_VALUE)
				.addGroup(gl_panelUsers.createSequentialGroup()
					.addGap(100)
					.addComponent(scrollPaneUsers, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(100, Short.MAX_VALUE))
		);
		gl_panelUsers.setVerticalGroup(
			gl_panelUsers.createParallelGroup(Alignment.LEADING)
				.addGap(0, 435, Short.MAX_VALUE)
				.addGroup(gl_panelUsers.createSequentialGroup()
					.addGap(25)
					.addComponent(scrollPaneUsers, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		tableUsers = new JTable();
		tableUsers.setBackground(new Color(255, 255, 255));
		tableUsers.getTableHeader().setBackground(Color.WHITE);
		
		//initialise the user table
		refreshUsers(null);
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//when click the delete text, call service and delete it
				int row = tableUsers.rowAtPoint(e.getPoint());
		        int column = tableUsers.columnAtPoint(e.getPoint());
		        
		        //only do the action if the client clicked operation column
		        if (column == 1) {
		        	String userToRemove = (String)tableUsers.getValueAt(row, 0); //get the user id of that user
		            System.out.println("Server clicked delete for: " + userToRemove);
		            
		            Server.removeConnection(userToRemove,false);
		        }
			}
		});
		scrollPaneUsers.setViewportView(tableUsers);
		panelUsers.setLayout(gl_panelUsers);
		frmServer.getContentPane().setLayout(groupLayout);

		//set the overview layout to be the default layout
		overviewLayout();

	}

	private void overviewLayout() {
		setPanelParentContent(panelUserCount);
	}

	private void dictionaryLayout() {
		setPanelParentContent(panelWords);
	}
	
	private void usersLayout() {
		setPanelParentContent(panelUsers);
	}

	private void cleanButtonLayout(JToggleButton btn) {
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
	}

	private void setPanelParentContent(JPanel... panels) {
		panelParent.removeAll();
		for (JPanel panel : panels) {
			panelParent.add(panel);
		}
		panelParent.revalidate();
		panelParent.repaint();
	}
	
	/**
	 * refresh the word list table
	 */
	public void refreshWords() {
		Set<String> wordSet= dictionaryService.getAllWords();
		
		//sort the word to display on UI
		List<String> wordList = new ArrayList<>(wordSet);
	    Collections.sort(wordList); 
	    
	    
		String[][] data = new String[wordSet.size()][2];//create data(words) to display on UI
		//add words and delete text to 2d array
		int i = 0;
		for (String word : wordList) {
			data[i][0]=word;
			data[i][1]="delete";
			i++;
		}
		
		String[] columnNames = { "Word", "Operation" };
		
		//update the table
		tableWords.setModel(new DefaultTableModel(
		        data,
		        columnNames
		    ) {
		        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
		            false, false
		        };

		        public boolean isCellEditable(int row, int column) {
		            return columnEditables[column];
		        }
		    });
		tableWords.getColumnModel().getColumn(0).setPreferredWidth(625);
		tableWords.getColumnModel().getColumn(0).setMinWidth(625);
		tableWords.getColumnModel().getColumn(1).setMinWidth(75);
		
		tableWords.setShowVerticalLines(false);
		tableWords.setRowSelectionAllowed(false);
		
		tableWords.setBackground(Color.WHITE);
		tableWords.setGridColor(Color.BLACK);
		tableWords.setShowGrid(true);
		tableWords.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Refresh the users list
	 * @param userIds new userId set
	 */
	public void refreshUsers(Set<String> userIds) {
		
		if (userIds == null || userIds.isEmpty()) {
			//if there is no user, clear the table
			tableUsers.setModel(new DefaultTableModel(
				new Object[0][2], new String[] { "User Id", "Operation" }
			));
			
			tableUsers.getColumnModel().getColumn(0).setPreferredWidth(625);
			tableUsers.getColumnModel().getColumn(0).setMinWidth(625);
			tableUsers.getColumnModel().getColumn(1).setMinWidth(75);
			
			tableUsers.setShowVerticalLines(false);
			tableUsers.setRowSelectionAllowed(false);
			return;
		}
		
		//sort the ids to display on UI
		List<String> userIdList = new ArrayList<>(userIds);
		Collections.sort(userIdList);
		
		String[][] data = new String[userIds.size()][2];//create data(words) to display on UI
		//add users and delete text to 2d array
		int i = 0;
		for (String id : userIdList) {
			data[i][0]=String.valueOf(id);
			data[i][1]="delete";
			i++;
		}
		
		String[] columnNames = { "User Id", "Operation" };
		
		//update the table
		tableUsers.setModel(new DefaultTableModel(
		        data,
		        columnNames
		    ) {
		        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
		            false, false
		        };

		        public boolean isCellEditable(int row, int column) {
		            return columnEditables[column];
		        }
		    });
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(625);
		tableUsers.getColumnModel().getColumn(0).setMinWidth(625);
		tableUsers.getColumnModel().getColumn(1).setMinWidth(75);
		
		tableUsers.setShowVerticalLines(false);
		tableUsers.setRowSelectionAllowed(false);
		
		tableUsers.setBackground(Color.WHITE);
		tableUsers.setGridColor(Color.BLACK);
		tableUsers.setShowGrid(true);
		tableUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * update the user count
	 * @param newNum new user count 
	 */
	public void updateUserCount(int newNum) {
		textFieldCurrentUserCount.setText(String.valueOf(newNum));
	}
	
	/**
	 * update the words count
	 * @param newNum new words count
	 */
	public void updateWordsCount(int newNum) {
		textFieldCurrentWordsCount.setText(String.valueOf(newNum));
	}
}
