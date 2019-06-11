package br.pro.hashi.ensino.desagil.aps.model;

public class AndGate extends Gate {
    private final NandGate nandLeft;
    private final NandGate nandRight;


    public AndGate() {
        super(2);

        nandLeft = new NandGate();
        nandRight = new NandGate();

        nandRight.connect(0, nandLeft);
        nandRight.connect(1, nandLeft);

    }


    @Override
    public boolean read() {
        return nandRight.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        switch (inputPin) {
            case 0:
                nandLeft.connect(0, emitter);
                break;

            case 1:
                nandLeft.connect(1, emitter);
                break;

            default:
                throw new IndexOutOfBoundsException(inputPin);
        }
    }
}
