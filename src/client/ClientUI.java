package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import constants.DictionaryConstant;
import dtos.ClientRequestDTO;
import dtos.ServerResponseDTO;
import enums.OperationType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Client UI
 */
public class ClientUI {
	private ClientSocketHandler clientSocketHandler;
	private JFrame frmClient;
	private JTextField textFieldInputWord;
	private JTextArea textAreaResult;
	private JLabel lblNewMeaning;
	private JTextArea textAreaNewMeaningInput;
	private JPanel panelNewMeaning;
	private JPanel panelOldMeaning;
	private JPanel panelResult;
	private JPanel panelWord;
	private JPanel panelParent;
	private JPanel panelMeaning;
	private JTextArea textAreaMeanings;
	private JTextArea textAreaOldMeaningInput;
	
	private OperationType currentOperationType=OperationType.SEARCH_WORD;
	private JPanel panelMeaningsInput;
	private JLabel lblMeaningsInput;
	private JTextArea textAreaMeaningsInput;
	private JTextField textFieldUserId;

	
	/**
	 * get current frame (to display warning message above)
	 * @return Jframe
	 */
	public JFrame getFrame() {
        return frmClient;
    }
	
	
	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI(ClientSocketHandler clientSocketHandler) {
		this.clientSocketHandler=clientSocketHandler;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.getContentPane().setBackground(Color.WHITE);
		frmClient.setType(Type.UTILITY);
		frmClient.setTitle("Client");
		frmClient.setBackground(Color.WHITE);
		frmClient.setBounds(100, 100, 917, 727);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//main button layout
		JButton btnOperation = new JButton("Search");
		btnOperation.setBackground(Color.BLACK); 
		btnOperation.setForeground(Color.WHITE);        
		btnOperation.setFont(new Font("Arial", Font.BOLD, 16)); 
		btnOperation.setFocusPainted(false); 
		btnOperation.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		JLabel lblClient = new JLabel("Client");
		lblClient.setFont(new Font("Arial", Font.PLAIN, 30));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOpaque(false);
		toolBar.setBackground(null);
		toolBar.setBorderPainted(false);
		
		//search word button
		JToggleButton tglbtnSearch = new JToggleButton("Search Word");
		tglbtnSearch.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnSearch.setSelected(true);
		tglbtnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOperation.setText(DictionaryConstant.TEXT_SEARCH);
				currentOperationType=OperationType.SEARCH_WORD;
				searchWordPanelLayout();
			}
		});
		cleanButtonLayout(tglbtnSearch);
		toolBar.add(tglbtnSearch);
		
		//add word button
		JToggleButton tglbtnAddWord = new JToggleButton("Add Word");
		tglbtnAddWord.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOperation.setText(DictionaryConstant.TEXT_ADD_WORD);
				currentOperationType=OperationType.ADD_WORD;
				addWordPanelLayout();
			}
		});
		cleanButtonLayout(tglbtnAddWord);
		toolBar.add(tglbtnAddWord);
		
		//delete word button
		JToggleButton tglbtnDeleteWord = new JToggleButton("Delete Word");
		tglbtnDeleteWord.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnDeleteWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOperation.setText(DictionaryConstant.TEXT_DELETE);
				currentOperationType=OperationType.DELETE_WORD;
				deleteWordPanelLayout();
			}
		});
		cleanButtonLayout(tglbtnDeleteWord);
		toolBar.add(tglbtnDeleteWord);
		
		
		//add meaning button
		JToggleButton tglbtnAddMeaning = new JToggleButton("Add Meaning");
		tglbtnAddMeaning.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnAddMeaning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOperation.setText(DictionaryConstant.TEXT_ADD_MEANING);
				currentOperationType=OperationType.ADD_MEANING;
				addMeaningPanelLayout();
			}
		});
		cleanButtonLayout(tglbtnAddMeaning);
		toolBar.add(tglbtnAddMeaning);
		
		//update meaning button
		JToggleButton tglbtnUpdateMeaning = new JToggleButton("Update Meaning");
		tglbtnUpdateMeaning.setFont(new Font("Calibri", Font.BOLD, 12));
		tglbtnUpdateMeaning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOperation.setText(DictionaryConstant.TEXT_UPDATE_MEANING);
				currentOperationType=OperationType.UPDATE_MEANING;
				updateMeaningPanelLayout();
			}
		});
		cleanButtonLayout(tglbtnUpdateMeaning);
		toolBar.add(tglbtnUpdateMeaning);
		
		
		
		
		ButtonGroup group = new ButtonGroup();
		group.add(tglbtnSearch);
		group.add(tglbtnAddWord);
		group.add(tglbtnDeleteWord);
		group.add(tglbtnAddMeaning);
		group.add(tglbtnUpdateMeaning);
		
		panelParent = new JPanel();
	
		textAreaMeanings=new JTextArea();
		
		panelResult = new JPanel();
		panelResult.setBackground(Color.WHITE);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setFont(new Font("Calibri", Font.BOLD, 20));
		
		textAreaResult = new JTextArea();
		textAreaResult.setFont(new Font("Calibri", Font.ITALIC, 16));
		textAreaResult.setBackground(Color.WHITE);
		textAreaResult.setEditable(false);
		
		panelOldMeaning = new JPanel();
		panelOldMeaning.setBackground(Color.WHITE);
		
		JLabel lblOldMeaning = new JLabel("Old Meaning:");
		lblOldMeaning.setFont(new Font("Calibri", Font.BOLD, 20));
		
		textAreaOldMeaningInput = new JTextArea();
		textAreaOldMeaningInput.setFont(new Font("Calibri", Font.PLAIN, 20));
		textAreaOldMeaningInput.setBackground(new Color(243, 243, 243));
		textAreaOldMeaningInput.setEditable(true);
		textAreaOldMeaningInput.setBorder(new LineBorder(Color.BLACK));
		
		
		GroupLayout gl_panelOldMeaning = new GroupLayout(panelOldMeaning);
		gl_panelOldMeaning.setHorizontalGroup(
			gl_panelOldMeaning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelOldMeaning.createSequentialGroup()
					.addGap(80)
					.addComponent(lblOldMeaning, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textAreaOldMeaningInput, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_panelOldMeaning.setVerticalGroup(
			gl_panelOldMeaning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelOldMeaning.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panelOldMeaning.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblOldMeaning, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(textAreaOldMeaningInput, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
		);
		panelOldMeaning.setLayout(gl_panelOldMeaning);
		
		panelNewMeaning = new JPanel();
		panelNewMeaning.setBackground(Color.WHITE);
		
		lblNewMeaning = new JLabel("New Meaning:");
		lblNewMeaning.setFont(new Font("Calibri", Font.BOLD, 20));
		
		//new meaning text box
		textAreaNewMeaningInput = new JTextArea();
		textAreaNewMeaningInput.setFont(new Font("Calibri", Font.PLAIN, 20));
		textAreaNewMeaningInput.setBackground(new Color(243, 243, 243));
		textAreaNewMeaningInput.setBorder(new LineBorder(Color.BLACK));
		textAreaNewMeaningInput.setBorder(new LineBorder(Color.BLACK));
		
		
		GroupLayout gl_panelNewMeaning = new GroupLayout(panelNewMeaning);
		gl_panelNewMeaning.setHorizontalGroup(
			gl_panelNewMeaning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNewMeaning.createSequentialGroup()
					.addGap(80)
					.addComponent(lblNewMeaning, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textAreaNewMeaningInput, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(180, Short.MAX_VALUE))
		);
		gl_panelNewMeaning.setVerticalGroup(
			gl_panelNewMeaning.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelNewMeaning.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewMeaning, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(71, 71, Short.MAX_VALUE))
				.addGroup(gl_panelNewMeaning.createSequentialGroup()
					.addGap(10)
					.addComponent(textAreaNewMeaningInput, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(10, Short.MAX_VALUE))
		);
		panelNewMeaning.setLayout(gl_panelNewMeaning);
		
		panelMeaning = new JPanel();
		panelMeaning.setBackground(Color.WHITE);
		
		JLabel lblMeaning = new JLabel("Meaning(s):");
		lblMeaning.setFont(new Font("Calibri", Font.BOLD, 20));
		
		JScrollPane scrollPaneMeanings = new JScrollPane();
		scrollPaneMeanings.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPaneMeanings.getHorizontalScrollBar().setBackground(Color.WHITE);
		scrollPaneMeanings.setViewportView(textAreaMeanings);
		scrollPaneMeanings.setBorder(null);
		
		GroupLayout gl_panelMeaning = new GroupLayout(panelMeaning);
		gl_panelMeaning.setHorizontalGroup(
			gl_panelMeaning.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMeaning.createSequentialGroup()
					.addGap(80)
					.addComponent(lblMeaning, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrollPaneMeanings, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(90, Short.MAX_VALUE))
		);
		gl_panelMeaning.setVerticalGroup(
			gl_panelMeaning.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMeaning.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panelMeaning.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMeaning, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_panelMeaning.createSequentialGroup()
							.addComponent(scrollPaneMeanings, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addGap(0)))
					.addGap(0, 0, Short.MAX_VALUE))
		);
		
		textAreaMeanings = new JTextArea();
		textAreaMeanings.setEditable(false);
		scrollPaneMeanings.setViewportView(textAreaMeanings);

		
		textAreaMeanings.setFont(new Font("Calibri", Font.PLAIN, 20));
		textAreaMeanings.setBackground(Color.WHITE);
		textAreaMeanings.setEditable(false);
		
		
		panelMeaning.setLayout(gl_panelMeaning);
		GroupLayout gl_panelResult = new GroupLayout(panelResult);
		gl_panelResult.setHorizontalGroup(
			gl_panelResult.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelResult.createSequentialGroup()
					.addGap(80)
					.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textAreaResult, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_panelResult.setVerticalGroup(
			gl_panelResult.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelResult.createSequentialGroup()
					.addContainerGap(37, Short.MAX_VALUE)
					.addGroup(gl_panelResult.createParallelGroup(Alignment.TRAILING)
						.addComponent(textAreaResult, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		panelResult.setLayout(gl_panelResult);
		
		textFieldUserId = new JTextField();
		textFieldUserId.setBackground(new Color(255, 255, 255));
		textFieldUserId.setEditable(false);
		textFieldUserId.setColumns(10);
		textFieldUserId.setBorder(null);

		
		JLabel lblUserId = new JLabel("ID:");
		lblUserId.setFont(new Font("Calibri", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(frmClient.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(242)
					.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
					.addGap(214))
				.addComponent(panelParent, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(440)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblClient, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUserId)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldUserId, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(423, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addComponent(lblClient, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldUserId, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUserId))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panelParent, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE))
		);
		


		//main logic (when the main button is pressed, use a clientRequestDTO to encapsulate the request and send to server  
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word=textFieldInputWord.getText();
				//send request to server only if the word is in input box
				if(word==null||word.isEmpty()) {
					clearAllText();
					textAreaResult.setText(DictionaryConstant.WORD_CANNOT_BE_EMPTY);
				}else {
					ClientRequestDTO clientRequestDTO=new ClientRequestDTO();
					clientRequestDTO.setOperationType(currentOperationType); //get current operation type and pass to server
					clientRequestDTO.setWord(word);
					
					//check for empty values before sending the request to server
					switch (currentOperationType) {
					case ADD_WORD: {
						//if the meaning box in empty, show failed on UI without send the request to server
						String meanings=textAreaMeaningsInput.getText();
						if(meanings==null || meanings.isEmpty()) {
							clearAllText();
							textAreaResult.setText(DictionaryConstant.MEANING_CANNOT_BE_EMPTY);
							return;
						}
						//set the meanings in DTO
						clientRequestDTO.setMeanings(textAreaMeaningsInput.getText());
						break;
						
					}
					case ADD_MEANING:{
						//if the new meaning box in empty, show failed on UI without send the request to server
						String newMeaning=textAreaNewMeaningInput.getText();
						if(newMeaning==null || newMeaning.isEmpty()) {
							clearAllText();
							textAreaResult.setText(DictionaryConstant.MEANING_CANNOT_BE_EMPTY);
							return;
						}
						clientRequestDTO.setNewMeaning(textAreaNewMeaningInput.getText());
						break;
					}
					case UPDATE_MEANING:{
						//if the old meaning or new meaning box in empty, show failed on UI without send the request to server
						String oldMeaning=textAreaOldMeaningInput.getText();
						String newMeaning=textAreaNewMeaningInput.getText();
						if(newMeaning==null || newMeaning.isEmpty()||oldMeaning==null || oldMeaning.isEmpty()) {
							clearAllText();
							textAreaResult.setText(DictionaryConstant.MEANING_CANNOT_BE_EMPTY);
							return;
						}
						//if the user enters the same meaning in old meaning and new meaning box
						if(oldMeaning.toLowerCase().equals(newMeaning.toLowerCase())){
							clearAllText();
							textAreaResult.setText(DictionaryConstant.MEANING_CANNOT_BE_SAME);
							return;
						}
						clientRequestDTO.setOldMeaning(textAreaOldMeaningInput.getText());
						clientRequestDTO.setNewMeaning(textAreaNewMeaningInput.getText());
						break;
					}
					default:
						clearAllText();
						textAreaResult.setText(DictionaryConstant.UNKNOWN_ERROR);
						break;
					}
					System.out.println("UI sends out the request:"+clientRequestDTO.toString());
					clientSocketHandler.sendToServer(clientRequestDTO);
					clearAllText();
					
				}
			}
		});
		
		btnOperation.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btnOperation.setBackground(new Color(73, 73, 73));
		    }

		    @Override
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btnOperation.setBackground(Color.BLACK); //change the color back
		    }
		});
		btnOperation.setFont(new Font("Calibri", Font.BOLD, 20));
		
		panelWord = new JPanel();
		panelWord.setBackground(Color.WHITE);
		
		JLabel lblInputWord = new JLabel("Word:");
		lblInputWord.setFont(new Font("Calibri", Font.BOLD, 20));
		
		textFieldInputWord = new JTextField();
		textFieldInputWord.setFont(new Font("Calibri", Font.PLAIN, 20));
		textFieldInputWord.setBackground(new Color(243, 243, 243));
		textFieldInputWord.setColumns(10);
		textFieldInputWord.setBorder(new LineBorder(Color.BLACK,2));
		
		
		
		GroupLayout gl_panelWord = new GroupLayout(panelWord);
		gl_panelWord.setHorizontalGroup(
			gl_panelWord.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWord.createSequentialGroup()
					.addGap(80)
					.addComponent(lblInputWord, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textFieldInputWord, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnOperation)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_panelWord.setVerticalGroup(
			gl_panelWord.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWord.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panelWord.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnOperation, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInputWord, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldInputWord, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addGap(13))
		);
		panelWord.setLayout(gl_panelWord);
		panelParent.setLayout(new BoxLayout(panelParent, BoxLayout.Y_AXIS));
		
		panelMeaningsInput = new JPanel();
		panelMeaningsInput.setBackground(Color.WHITE);
		panelParent.add(panelMeaningsInput);
		
		lblMeaningsInput = new JLabel("Meaning(s):");
		lblMeaningsInput.setFont(new Font("Calibri", Font.BOLD, 20));
		
		textAreaMeaningsInput = new JTextArea();
		textAreaMeaningsInput.setText("");
		textAreaMeaningsInput.setFont(new Font("Calibri", Font.PLAIN, 20));
		textAreaMeaningsInput.setBorder(new LineBorder(Color.BLACK));
		textAreaMeaningsInput.setBackground(new Color(243, 243, 243));
		GroupLayout gl_panelMeaningsInput = new GroupLayout(panelMeaningsInput);
		gl_panelMeaningsInput.setHorizontalGroup(
			gl_panelMeaningsInput.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMeaningsInput.createSequentialGroup()
					.addGap(80)
					.addComponent(lblMeaningsInput, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textAreaMeaningsInput, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(180, Short.MAX_VALUE))
		);
		gl_panelMeaningsInput.setVerticalGroup(
			gl_panelMeaningsInput.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMeaningsInput.createSequentialGroup()
					.addGroup(gl_panelMeaningsInput.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMeaningsInput.createSequentialGroup()
							.addGap(10)
							.addComponent(lblMeaningsInput, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelMeaningsInput.createSequentialGroup()
							.addGap(10)
							.addComponent(textAreaMeaningsInput, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panelMeaningsInput.setLayout(gl_panelMeaningsInput);
		panelParent.add(panelOldMeaning);
		panelParent.add(panelNewMeaning);
		panelParent.add(panelWord);
		panelParent.add(panelMeaning);
		panelParent.add(panelResult);
		frmClient.getContentPane().setLayout(groupLayout);
		
		
		
		lblNewMeaning = new JLabel("New Meaning:");
		lblNewMeaning.setFont(new Font("宋体", Font.PLAIN, 20));
	
		//make the search layout the default when the UI starts
		searchWordPanelLayout();

	}
	
	/**
	 * show response from the server
	 * @param serverResponseDTO serverResponseDTO
	 */
	public void showResult(ServerResponseDTO serverResponseDTO) {
		//if current operation is search, display the meanings send by the server
		if(currentOperationType==OperationType.SEARCH_WORD) {
			//do string builder only if the search is successful
			if(serverResponseDTO.getCode()==DictionaryConstant.CODE_SUCCESS) {
				StringBuilder meanings=new StringBuilder();
				for (int i = 0; i < serverResponseDTO.getContentList().size(); i++) {
					meanings.append((i + 1)).append(". ").append(serverResponseDTO.getContentList().get(i)).append("\n");
				}
				textAreaMeanings.setText(meanings.toString());
			}
		}
		
		//set the msg come from the server
		textAreaResult.setText(serverResponseDTO.getMsg());
	}
	
	/**
	 * display current user id
	 * @param userId user id
	 */
	public void showUserId(String userId) {
		textFieldUserId.setText(userId);
	}
	
	
	private void cleanButtonLayout(JToggleButton btn) {
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
	}
	
	private void searchWordPanelLayout() {
		setPanelParentContent(panelWord, panelMeaning, panelResult);
	}
	
	private void addWordPanelLayout() {
		setPanelParentContent(panelWord, panelMeaningsInput, panelResult);
	}
	
	private void deleteWordPanelLayout() {
		setPanelParentContent(panelWord, panelResult);
	}
	
	private void addMeaningPanelLayout() {
		setPanelParentContent(panelWord, panelNewMeaning, panelResult);
	}
	
	private void updateMeaningPanelLayout() {
		setPanelParentContent(panelWord, panelOldMeaning, panelNewMeaning, panelResult);
	}
	
	
	private void setPanelParentContent(JPanel... panels) {
		clearAllText();
	    panelParent.removeAll();
	    for (JPanel panel : panels) {
	        panelParent.add(panel);
	    }
	    panelParent.revalidate();
	    panelParent.repaint();
	}
	
	private void clearAllText() {
		if (textFieldInputWord != null)
		    textFieldInputWord.setText("");
		if (textAreaResult != null)
		    textAreaResult.setText("");
		if (textAreaMeanings != null)
		    textAreaMeanings.setText("");
		if (textAreaNewMeaningInput != null)
		    textAreaNewMeaningInput.setText("");
		if (textAreaOldMeaningInput != null)
		    textAreaOldMeaningInput.setText("");
		if (textAreaMeaningsInput != null)
		    textAreaMeaningsInput.setText("");
	}
}
