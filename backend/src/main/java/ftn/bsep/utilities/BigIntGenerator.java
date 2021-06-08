package ftn.bsep.utilities;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
public class BigIntGenerator {

    public BigInteger generateRandom() {

        BigInteger maxLimit = new BigInteger("5000000000000");
        BigInteger minLimit = new BigInteger("25000000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = bigInteger.bitLength();
        BigInteger res = new BigInteger(len, randNum);

        return res;
    }
}