package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;
    private final Light light;
    private final JCheckBox[] inputBoxes;

    private final Image image;


    public GateView(Gate gate) {
        super(300, 265);
        this.gate = gate;
        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];
        light = new Light();
        light.setR(255);
        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();
            gate.connect(i, switches[i]);
        }


        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int contador = 0;
        for (JCheckBox inputBox : inputBoxes) {
            add(inputBox, 15, 58 + contador, 20, 20);
            contador += 90;
            if (inputSize == 1) {
                add(inputBox, 18, 105, 20, 20);
            }
        }

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        addMouseListener(this);

        update();
    }

    private void update() {
        this.light.connect(0, gate);
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }
        repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        Color color;
        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();
        Color lightColor = new Color(light.getR(), light.getG(), light.getB());
        // Se o clique foi dentro do quadrado colorido...
        if (Math.pow(x - 270, 2) + Math.pow(y - 115, 2) < Math.pow(15, 2)) {

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, lightColor);
            light.setR(color.getRed());
            light.setG(color.getGreen());
            light.setB(color.getBlue());
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 40, 0, 221, 221, this);

        // Desenha um quadrado cheio.
        Color circle = new Color(light.getR(), light.getG(), light.getB());

        g.setColor(circle);
        g.fillOval(255, 100, 30, 30);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
