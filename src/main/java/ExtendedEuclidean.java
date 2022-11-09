import java.math.BigInteger;

public class ExtendedEuclidean {
    private BigInteger e;
    private final BigInteger fi;
    private final ConstantsGenerator constantsGenerator;
    private BigInteger k = BigInteger.ZERO;
    private BigInteger d = BigInteger.ONE;

    public ExtendedEuclidean(ConstantsGenerator constantsGenerator) {
        this.constantsGenerator = constantsGenerator;
        this.e = constantsGenerator.getE();
        this.fi = constantsGenerator.getFi();
    }

    public void calculate() {
        while(this.k.abs().multiply(this.fi).add(BigInteger.ONE).divide(this.e).compareTo(this.d.abs()) != 0){
            this.constantsGenerator.generateNewE();
            this.e = constantsGenerator.getE();
            gcdExtended(this.fi, this.e);
            System.out.println("result= " + this.k.abs().multiply(this.fi).add(BigInteger.ONE).divide(this.e));
        }
        System.out.println("k=" + this.k);
        System.out.println("d=" + this.d);
        System.out.println(this.k.abs().multiply(this.fi).add(BigInteger.ONE).divide(this.e).compareTo(this.d.abs()) == 0);
    }

    public void gcdExtended(BigInteger a, BigInteger b)
    {
        BigInteger x = BigInteger.ZERO, y = BigInteger.ONE, lastx = BigInteger.ONE, lasty = BigInteger.ZERO, temp;
        while (b.compareTo(BigInteger.ZERO) != 0)
        {
            BigInteger q = a.divide(b);
            BigInteger r = a.mod(b);

            a = b;
            b = r;

            temp = x;
            x = lastx.subtract(q.multiply(x));
            lastx = temp;

            temp = y;
            y = lasty.subtract(q.multiply(y));
            lasty = temp;
        }
        this.k = lastx;
        this.d = lasty;
    }

    public BigInteger getD() {
        return d;
    }
}
