package com.motorola.window;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScreenMain {

	private JFrame frame;
	private static JTextField tfNumKp;
	private static JTextField tfKmKp;

	

	/**
	 * Launch the application.
	 */
	public static void startScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenMain window = new ScreenMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ScreenMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tfNumKp = new JTextField();
		tfNumKp.setText("455");
		tfNumKp.setBounds(87, 43, 55, 22);
		frame.getContentPane().add(tfNumKp);
		tfNumKp.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("КП №");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(33, 45, 55, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("км");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(221, 45, 55, 16);
		frame.getContentPane().add(label);
		
		tfKmKp = new JTextField();
		tfKmKp.setColumns(10);
		tfKmKp.setBounds(154, 43, 55, 22);
		frame.getContentPane().add(tfKmKp);
		
		JLabel lblNewLabel_1 = new JLabel("ШТМ ЛТМ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(108, 13, 140, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JDialog dialog = new JDialog();
				tfKmKp.setText("1212");
			}
		});
		btnNewButton.setBounds(81, 145, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
		
	}
	public void run() {
		try {
			ScreenMain window = new ScreenMain();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
