package lianlu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Timers extends JFrame {

	// 定义组件
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timers frame = new Timers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Timers() {
		setTitle("MyTimers - for Shuyue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(500, 700);
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(4, 4, 4, 4));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);

		JPanel mainPanel = new JPanel(new GridLayout(10, 1));
		mainPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(mainPanel, BorderLayout.CENTER);

		JTextField jf = new JTextField("Please input project name");
		jf.setForeground(Color.LIGHT_GRAY);

		jf.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (jf.getText().equals("Please input project name")) {
					jf.setText("");
					jf.setForeground(Color.BLACK);
				}
				

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (jf.getText().equals("Please input project name"))
						return;
					String pname = jf.getText().trim();
					if (pname.length() == 0)
						return;

					TimerColumn tc = new TimerColumn(pname);
					map.put(pname, tc);

					mainPanel.add(tc, BorderLayout.WEST);
					mainPanel.validate();

					jf.setText("Please input project name");
					jf.setForeground(Color.LIGHT_GRAY);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		mainPanel.add(jf, BorderLayout.SOUTH);

		JButton exportButton = new JButton("Export report");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					exportReport();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		mainPanel.add(exportButton);
	}

	private Map<String, TimerColumn> map = new HashMap();

	private void exportReport() throws IOException {
		String fileName = "";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		fileName = sdf.format(now);

		PrintWriter pw = new PrintWriter(new File("TimeReport - " + fileName + ".csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Project Name");
		sb.append(',');
		sb.append("Total Time (in hours)");
		sb.append(',');
		sb.append("Total Time (in seconds)");
		sb.append(',');
		sb.append("Detail Time");
		sb.append('\n');

		DecimalFormat df = new DecimalFormat("0.00");

		List<String> kSet = new LinkedList<>(map.keySet());
		Collections.sort(kSet);

		for (String project : kSet) {
			sb.append(project).append(",");

			TimerColumn tc = map.get(project);

			double hours = map.get(project).computeHours();
			sb.append(df.format(hours)).append(",");
			sb.append(tc.timer.toSeconds()).append(",");
			for (String timeInterval : tc.getIntervals()) {
				sb.append(timeInterval).append(",");
			}

			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
	}
}
