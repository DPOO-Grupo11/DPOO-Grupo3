package interfaz.menucliente;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LoggedInMenu {
	
	public LoggedInMenu() {
		JPanel panel= new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton crear=new JButton("Crear Reserva");
		
		crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	CrearReservaFrame reserva= new CrearReservaFrame();
            }
        });
		panel.add(crear);
		
		JButton modificar=new JButton("Modificar Reserva");
		
		crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	CrearReservaFrame reserva= new CrearReservaFrame();
            }
        });
		panel.add(modificar);
		
		JButton salir=new JButton("Salir");
		
		crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	System.exit(0);;
            }
        });
		panel.add(crear);
	}

}
