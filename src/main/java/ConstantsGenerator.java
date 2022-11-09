import java.math.BigInteger;
import java.security.SecureRandom;

public class ConstantsGenerator {
    private BigInteger p = BigInteger.ZERO;
    private BigInteger q = BigInteger.ZERO;
    private final BigInteger n;
    private final BigInteger fi;
    private BigInteger e;

    public ConstantsGenerator() {
        while(this.p.compareTo(q) == 0){
            this.p = BigInteger.probablePrime(160, new SecureRandom());
            this.q = BigInteger.probablePrime(160, new SecureRandom());
        }
        this.n = this.p.multiply(this.q);
        this.fi = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
        this.generateNewE();
    }

    public void generateNewE() {
        this.e = generateNumberInRange(this.fi);
    }

    private BigInteger generateNumberInRange(BigInteger upperLimit) {
        BigInteger bigInteger1 = upperLimit.subtract(BigInteger.ONE);
        SecureRandom rnd = new SecureRandom();
        int maxNumBitLength = upperLimit.bitLength();

        BigInteger aRandomBigInt;

        aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
        if (aRandomBigInt.compareTo(BigInteger.ONE) < 0)
            aRandomBigInt = aRandomBigInt.add(BigInteger.ONE);
        if (aRandomBigInt.compareTo(upperLimit) >= 0)
            aRandomBigInt = aRandomBigInt.mod(bigInteger1).add(BigInteger.ONE);
        return aRandomBigInt;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getFi() {
        return fi;
    }

    public BigInteger getE() {
        return e;
    }
}
