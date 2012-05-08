package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import listeners.actions.menu.file.ExitAction;
import listeners.actions.menu.file.LoadCardsAction;
import listeners.actions.menu.file.LoadProjectAction;
import listeners.actions.menu.file.NewAction;
import listeners.actions.menu.file.SaveProjectAction;
import listeners.actions.navigator.NextCardAction;
import listeners.actions.navigator.PreviousCardAction;
import listeners.actions.navigator.RemoveCardAction;
import model.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	@SuppressWarnings("unused")
	private static final Logger	log	= LoggerFactory.getLogger(MainWindow.class);
	
	private Model				model;
	private CardPanel			cardPanel;
	
	public MainWindow(Model model) {
		this.model = model;
		createAndShowGUI(model);
	}
	
	private void createAndShowGUI(Model model) {
		// Create and set up the window.
		setTitle("BulletHoleDetector");
		
		addWindowListener(new MainWindowListener());
		
		// Set up the content pane, where the "main GUI" lives.
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		// Start creating and adding components.
		// Card panel
		cardPanel = new CardPanel(model);
		
		// Menu
		setJMenuBar(buildMenuBar(model));
		
		// Adding components to JPanel, which is then added to the contentPane
		contentPane.add(BorderLayout.CENTER, cardPanel);
		add(contentPane);
		
		// set a minimum size for the window so the image panel is always visible
		setMinimumSize(new Dimension(240, 320));
		
		// Show the window.
		pack();
	}
	
	private JMenuBar buildMenuBar(Model model) {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem menuItemNew = new JMenuItem(new NewAction(model));
		menuItemNew.setName("menuItemNew");
		fileMenu.add(menuItemNew);
		
		JMenuItem menuItemLoadProject = new JMenuItem(new LoadProjectAction(model));
		menuItemLoadProject.setName("menuItemLoadProject");
		fileMenu.add(menuItemLoadProject);
		
		JMenuItem menuItemSaveProject = new JMenuItem(new SaveProjectAction(model));
		menuItemSaveProject.setName("menuItemSaveProject");
		fileMenu.add(menuItemSaveProject);
		
		JMenuItem menuItemLoadCards = new JMenuItem(new LoadCardsAction(model));
		menuItemLoadCards.setName("menuItemLoadCards");
		fileMenu.add(menuItemLoadCards);
		
		JMenuItem menuItemExit = new JMenuItem(new ExitAction(model));
		menuItemExit.setName("menuItemExit");
		fileMenu.add(menuItemExit);
		
		menuBar.add(fileMenu);
		
		JMenu navigateMenu = new JMenu("Navigate");
		
		JMenuItem menuItemPreviousCard = new JMenuItem(new PreviousCardAction(model));
		menuItemPreviousCard.setName("Previous card");
		navigateMenu.add(menuItemPreviousCard);
		
		JMenuItem menuItemNextCard = new JMenuItem(new NextCardAction(model));
		menuItemNextCard.setName("Next card");
		navigateMenu.add(menuItemNextCard);
		
		JMenuItem menuItemRemoveCard = new JMenuItem(new RemoveCardAction(model));
		menuItemRemoveCard.setName("Remove card");
		navigateMenu.add(menuItemRemoveCard);
		
		menuBar.add(navigateMenu);
		
		return menuBar;
	}
	
	private class MainWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			new ExitAction(model).actionPerformed(new ActionEvent(e.getSource(), e.getID(), "Exit"));
		}
	}
}