import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RSAFrame extends JFrame implements ActionListener {
    private Container container;
    private JLabel inputTextLabel;
    private JTextArea inputText;
    private JLabel publicKeyLabel;
    private JLabel publicKey;
    private JLabel privateKeyLabel;
    private JLabel privateKey;
    private JLabel encryptedTextLabel;
    private JTextArea encryptedText;
    private JLabel decryptedTextLabel;
    private JTextArea decryptedText;
    private JButton submit;

    private BigInteger e;
    private BigInteger d;
    private BigInteger n;

    Map<Integer, BigInteger> dictionary = new HashMap<>();

    public RSAFrame() {
        setTitle("RSA encryptor");

        setBounds(300, 90, 1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        container = getContentPane();
        container.setLayout(null);

        inputTextLabel = new JLabel("Input text to encrypt");
        inputTextLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        inputTextLabel.setSize(200, 20);
        inputTextLabel.setLocation(50, 50);
        container.add(inputTextLabel);

        inputText = new JTextArea();
        inputText.setFont(new Font("Arial", Font.PLAIN, 15));
        inputText.setSize(500, 200);
        inputText.setLocation(250, 50);
        container.add(inputText);

        publicKeyLabel = new JLabel("Public key");
        publicKeyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        publicKeyLabel.setSize(200, 20);
        publicKeyLabel.setLocation(50, 270);
        container.add(publicKeyLabel);

        publicKey = new JLabel();
        publicKey.setFont(new Font("Arial", Font.PLAIN, 15));
        publicKey.setSize(200, 20);
        publicKey.setLocation(250, 270);
        container.add(publicKey);

        privateKeyLabel = new JLabel("Private key");
        privateKeyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        privateKeyLabel.setSize(200, 20);
        privateKeyLabel.setLocation(50, 300);
        container.add(privateKeyLabel);

        privateKey = new JLabel();
        privateKey.setFont(new Font("Arial", Font.PLAIN, 15));
        privateKey.setSize(200, 20);
        privateKey.setLocation(250, 300);
        container.add(privateKey);

        encryptedTextLabel = new JLabel("Encrypted text");
        encryptedTextLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        encryptedTextLabel.setSize(200, 20);
        encryptedTextLabel.setLocation(50, 330);
        container.add(encryptedTextLabel);

        encryptedText = new JTextArea();
        encryptedText.setFont(new Font("Arial", Font.PLAIN, 15));
        encryptedText.setSize(500, 200);
        encryptedText.setLocation(250, 330);
        container.add(encryptedText);

        decryptedTextLabel = new JLabel("Decrypted text");
        decryptedTextLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        decryptedTextLabel.setSize(200, 20);
        decryptedTextLabel.setLocation(50, 550);
        container.add(decryptedTextLabel);

        decryptedText = new JTextArea();
        decryptedText.setFont(new Font("Arial", Font.PLAIN, 15));
        decryptedText.setSize(500, 200);
        decryptedText.setLocation(250, 550);
        container.add(decryptedText);

        submit = new JButton("Submit");
        submit.setSize(100, 50);
        submit.setLocation(780, 50);
        submit.addActionListener(this);
        container.add(submit);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder encryptedTextString = new StringBuilder();
        StringBuilder decryptedTextString = new StringBuilder();
        Supplier<IntStream> characterStream = () -> inputText.getText().chars();
        characterStream.get().findFirst().ifPresent(c -> {
            BigInteger encryptedMessage = BigInteger.ZERO;
            BigInteger decriptedValue = BigInteger.ZERO;
            BigInteger charValue;
            charValue = BigInteger.valueOf(c);
            while(charValue.compareTo(decriptedValue) !=0 ) {
                ConstantsGenerator constantsGenerator = new ConstantsGenerator();
                ExtendedEuclidean extendedEuclidean = new ExtendedEuclidean(constantsGenerator);
                extendedEuclidean.calculate();
                //encrypt
                encryptedMessage = charValue.modPow(constantsGenerator.getE(), constantsGenerator.getN());
                decriptedValue = encryptedMessage.modPow(extendedEuclidean.getD(), constantsGenerator.getN());
                System.out.println(encryptedMessage);
                System.out.println(decriptedValue);
                this.d = extendedEuclidean.getD();
                this.e = constantsGenerator.getE();
                this.n = constantsGenerator.getN();
            }
            this.dictionary.put(c, encryptedMessage);
            encryptedTextString.append(encryptedMessage);
            decryptedTextString.append((char) decriptedValue.intValue());
        });
        characterStream.get().skip(1).forEach(c -> {
            if(!this.dictionary.containsKey(c)) {
                BigInteger encryptedMessage;
                BigInteger decriptedValue;
                BigInteger charValue;
                charValue = BigInteger.valueOf(c);
                encryptedMessage = charValue.modPow(this.e, this.n);
                decriptedValue = encryptedMessage.modPow(this.d, this.n);
                System.out.println(encryptedMessage);
                System.out.println(decriptedValue);
            }
            encryptedTextString.append(this.dictionary.get(c));
            decryptedTextString.append((char) c);
        });

        encryptedText.append(encryptedTextString.toString());
        decryptedText.append(decryptedTextString.toString());
    }
}
