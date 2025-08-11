//package com.hmdm.util;
//
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * Utility class for password generation, hashing, and validation.
// */
//public final class PasswordUtil {
//
//    public static final int PASS_STRENGTH_NONE = 0;
//    public static final int PASS_STRENGTH_ALPHADIGIT = 1;
//    public static final int PASS_STRENGTH_SPECIAL = 2;
//
//    private static final String PASS_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-.,!#$%()=+;*/";
//    private static final int DIGIT_START = 0;
//    private static final int DIGIT_END = 9;
//    private static final int ALPHA_LOWER_START = 10;
//    private static final int ALPHA_LOWER_END = 35;
//    private static final int ALPHA_CAPS_START = 36;
//    private static final int ALPHA_CAPS_END = 61;
//    private static final int ALPHA_CHAR_START = 62;
//    private static final int ALPHA_CHAR_END = 76;
//
//    private static final String PASS_SALT = "5YdSYHyg2U";
//
//    private PasswordUtil() {
//        // Utility class; prevent instantiation
//    }
//
//    public static String getHashFromRaw(String password) {
//        String md5 = CryptoUtil.getMD5String(password);
//        return getHashFromMd5(md5);
//    }
//
//    public static String getHashFromMd5(String md5) {
//        return CryptoUtil.getSHA1String(md5 + PASS_SALT);
//    }
//
//    public static boolean passwordMatch(String enteredPass, String dbPass) {
//        return getHashFromMd5(enteredPass).equalsIgnoreCase(dbPass);
//    }
//
//    public static boolean checkPassword(String password, int length, int strength) {
//        if (password.length() < length) {
//            return false;
//        }
//
//        boolean hasDigits = false;
//        boolean hasLower = false;
//        boolean hasCaps = false;
//        boolean hasSpecial = false;
//
//        for (char ch : password.toCharArray()) {
//            int i = PASS_CHARS.indexOf(ch);
//            if (i == -1) {
//                hasSpecial = true;
//            } else if (i >= DIGIT_START && i <= DIGIT_END) {
//                hasDigits = true;
//            } else if (i >= ALPHA_LOWER_START && i <= ALPHA_LOWER_END) {
//                hasLower = true;
//            } else if (i >= ALPHA_CAPS_START && i <= ALPHA_CAPS_END) {
//                hasCaps = true;
//            } else if (i >= ALPHA_CHAR_START && i <= ALPHA_CHAR_END) {
//                hasSpecial = true;
//            }
//        }
//
//        return switch (strength) {
//            case PASS_STRENGTH_ALPHADIGIT -> hasDigits && hasLower && hasCaps;
//            case PASS_STRENGTH_SPECIAL -> hasDigits && hasLower && hasCaps && hasSpecial;
//            case PASS_STRENGTH_NONE -> true;
//            default -> false; // Reserved or unknown strength
//        };
//    }
//
//    public static String generatePassword(int length, int strength) {
//        int realLength = Math.max(length, 8);
//
//        int charIntervalEnd = switch (strength) {
//            case PASS_STRENGTH_ALPHADIGIT -> ALPHA_CAPS_END;
//            case PASS_STRENGTH_SPECIAL -> ALPHA_CHAR_END;
//            default -> DIGIT_END;
//        };
//
//        StringBuilder b = new StringBuilder();
//        var random = ThreadLocalRandom.current();
//        for (int i = 0; i < realLength - 3; i++) {
//            int index = random.nextInt(charIntervalEnd + 1);
//            b.append(PASS_CHARS.charAt(index));
//        }
//
//        if (!checkPassword(b.toString(), length, strength)) {
//            // Append required types
//            b.append(PASS_CHARS.charAt(random.nextInt(DIGIT_END + 1)));
//            b.append(PASS_CHARS.charAt(ALPHA_LOWER_START + random.nextInt(ALPHA_LOWER_END - ALPHA_LOWER_START + 1)));
//            b.append(PASS_CHARS.charAt(ALPHA_CAPS_START + random.nextInt(ALPHA_CAPS_END - ALPHA_CAPS_START + 1)));
//            if (strength == PASS_STRENGTH_SPECIAL) {
//                b.append(PASS_CHARS.charAt(ALPHA_CHAR_START + random.nextInt(ALPHA_CHAR_END - ALPHA_CHAR_START + 1)));
//            }
//        } else {
//            for (int i = 0; i < 3; i++) {
//                int index = random.nextInt(charIntervalEnd + 1);
//                b.append(PASS_CHARS.charAt(index));
//            }
//        }
//
//        return b.toString();
//    }
//
//    public static String generateToken() {
//        var random = ThreadLocalRandom.current();
//        StringBuilder b = new StringBuilder();
//        for (int i = 0; i < 20; i++) {
//            b.append(PASS_CHARS.charAt(random.nextInt(ALPHA_CAPS_END)));
//        }
//        return b.toString();
//    }
//}
//
//package com.hmdm.util;
//
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * Utility class for password generation, hashing, and validation.
// */
//public final class PasswordUtil {
//
//    public static final int PASS_STRENGTH_NONE = 0;
//    public static final int PASS_STRENGTH_ALPHADIGIT = 1;
//    public static final int PASS_STRENGTH_SPECIAL = 2;
//
//    private static final String PASS_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-.,!#$%()=+;*/";
//    private static final int DIGIT_START = 0;
//    private static final int DIGIT_END = 9;
//    private static final int ALPHA_LOWER_START = 10;
//    private static final int ALPHA_LOWER_END = 35;
//    private static final int ALPHA_CAPS_START = 36;
//    private static final int ALPHA_CAPS_END = 61;
//    private static final int ALPHA_CHAR_START = 62;
//    private static final int ALPHA_CHAR_END = 76;
//
//    private PasswordUtil() {
//        // Utility class; prevent instantiation
//    }
//
//    /**
//     * Returns an MD5 hash of the raw password string.
//     */
//    public static String getHashFromRaw(String password) {
//        return CryptoUtil.getMD5String(password+);
//    }
//
//    /**
//     * Compares the hashed value of the entered password with the one stored in the DB.
//     */
//    public static boolean passwordMatch(String enteredPass, String dbPass) {
//        return getHashFromRaw(enteredPass).equalsIgnoreCase(dbPass);
//    }
//
//    public static boolean checkPassword(String password, int length, int strength) {
//        if (password.length() < length) {
//            return false;
//        }
//
//        boolean hasDigits = false;
//        boolean hasLower = false;
//        boolean hasCaps = false;
//        boolean hasSpecial = false;
//
//        for (char ch : password.toCharArray()) {
//            int i = PASS_CHARS.indexOf(ch);
//            if (i == -1) {
//                hasSpecial = true;
//            } else if (i >= DIGIT_START && i <= DIGIT_END) {
//                hasDigits = true;
//            } else if (i >= ALPHA_LOWER_START && i <= ALPHA_LOWER_END) {
//                hasLower = true;
//            } else if (i >= ALPHA_CAPS_START && i <= ALPHA_CAPS_END) {
//                hasCaps = true;
//            } else if (i >= ALPHA_CHAR_START && i <= ALPHA_CHAR_END) {
//                hasSpecial = true;
//            }
//        }
//
//        return switch (strength) {
//            case PASS_STRENGTH_ALPHADIGIT -> hasDigits && hasLower && hasCaps;
//            case PASS_STRENGTH_SPECIAL -> hasDigits && hasLower && hasCaps && hasSpecial;
//            case PASS_STRENGTH_NONE -> true;
//            default -> false; // Reserved or unknown strength
//        };
//    }
//
//    public static String generatePassword(int length, int strength) {
//        int realLength = Math.max(length, 8);
//
//        int charIntervalEnd = switch (strength) {
//            case PASS_STRENGTH_ALPHADIGIT -> ALPHA_CAPS_END;
//            case PASS_STRENGTH_SPECIAL -> ALPHA_CHAR_END;
//            default -> DIGIT_END;
//        };
//
//        StringBuilder b = new StringBuilder();
//        var random = ThreadLocalRandom.current();
//        for (int i = 0; i < realLength - 3; i++) {
//            int index = random.nextInt(charIntervalEnd + 1);
//            b.append(PASS_CHARS.charAt(index));
//        }
//
//        if (!checkPassword(b.toString(), length, strength)) {
//            // Append required types
//            b.append(PASS_CHARS.charAt(random.nextInt(DIGIT_END + 1)));
//            b.append(PASS_CHARS.charAt(ALPHA_LOWER_START + random.nextInt(ALPHA_LOWER_END - ALPHA_LOWER_START + 1)));
//            b.append(PASS_CHARS.charAt(ALPHA_CAPS_START + random.nextInt(ALPHA_CAPS_END - ALPHA_CAPS_START + 1)));
//            if (strength == PASS_STRENGTH_SPECIAL) {
//                b.append(PASS_CHARS.charAt(ALPHA_CHAR_START + random.nextInt(ALPHA_CHAR_END - ALPHA_CHAR_START + 1)));
//            }
//        } else {
//            for (int i = 0; i < 3; i++) {
//                int index = random.nextInt(charIntervalEnd + 1);
//                b.append(PASS_CHARS.charAt(index));
//            }
//        }
//
//        return b.toString();
//    }
//
//    public static String generateToken() {
//        var random = ThreadLocalRandom.current();
//        StringBuilder b = new StringBuilder();
//        for (int i = 0; i < 20; i++) {
//            b.append(PASS_CHARS.charAt(random.nextInt(ALPHA_CAPS_END)));
//        }
//        return b.toString();
//    }
//}


// spring managed bean

package com.hmdm.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordUtil {

    public static final int PASS_STRENGTH_NONE = 0;
    public static final int PASS_STRENGTH_ALPHADIGIT = 1;
    public static final int PASS_STRENGTH_SPECIAL = 2;

    private final Random random = new Random();

    private final String PASS_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-.,!#$%()=+;*/";
    private final int DIGIT_START = 0;
    private final int DIGIT_END = 9;
    private final int ALPHA_LOWER_START = 10;
    private final int ALPHA_LOWER_END = 35;
    private final int ALPHA_CAPS_START = 36;
    private final int ALPHA_CAPS_END = 61;
    private final int ALPHA_CHAR_START = 62;
    private final int ALPHA_CHAR_END = 76;

    private final String PASS_SALT = "5YdSYHyg2U";

    private final CryptoUtil cryptoUtil;

    // ✅ Constructor Injection of CryptoUtil
    public PasswordUtil(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    public String getHashFromRaw(String password) {
        String md5 = cryptoUtil.getMD5String(password);
        return getHashFromMd5(md5);
    }

    public String getHashFromMd5(String md5) {
        String combined = md5 + PASS_SALT;
//        System.out.println("🔍 Combined input to SHA1: " + combined);
        return cryptoUtil.getSHA1String(combined);
    }

    public boolean passwordMatch(String enteredPass, String dbPass) {
//        System.out.println("🔍 enteredPass to getHAshFromMd5: " + enteredPass);
        return getHashFromMd5(enteredPass).equalsIgnoreCase(dbPass);
    }

    public boolean checkPassword(String password, int length, int strength) {
        if (password.length() < length) {
            return false;
        }

        if (strength == PASS_STRENGTH_NONE) {
            return true;
        }

        boolean hasDigits = false;
        boolean hasLower = false;
        boolean hasCaps = false;
        boolean hasSpecial = false;

        for (int n = 0; n < password.length(); n++) {
            int i = PASS_CHARS.indexOf(password.charAt(n));
            if (i == -1) {
                hasSpecial = true;
            } else if (i >= DIGIT_START && i <= DIGIT_END) {
                hasDigits = true;
            } else if (i >= ALPHA_LOWER_START && i <= ALPHA_LOWER_END) {
                hasLower = true;
            } else if (i >= ALPHA_CAPS_START && i <= ALPHA_CAPS_END) {
                hasCaps = true;
            } else if (i >= ALPHA_CHAR_START && i <= ALPHA_CHAR_END) {
                hasSpecial = true;
            }
        }

        if (strength == PASS_STRENGTH_ALPHADIGIT) {
            return hasDigits && hasLower && hasCaps;
        }

        if (strength == PASS_STRENGTH_SPECIAL) {
            return hasDigits && hasLower && hasCaps && hasSpecial;
        }

        return false;
    }

    public String generatePassword(int length, int strength) {
        int realLength = length < 8 ? 8 : length;

        int charIntervalEnd = DIGIT_END;
        switch (strength) {
            case PASS_STRENGTH_NONE:
                charIntervalEnd = DIGIT_END;
                break;
            case PASS_STRENGTH_ALPHADIGIT:
                charIntervalEnd = ALPHA_CAPS_END;
                break;
            case PASS_STRENGTH_SPECIAL:
                charIntervalEnd = ALPHA_CHAR_END;
                break;
        }

        StringBuilder b = new StringBuilder();
        for (int n = 0; n < realLength - 3; n++) {
            int index = random.nextInt(charIntervalEnd + 1);
            b.append(PASS_CHARS.charAt(index));
        }

        String password = b.toString();

        if (!checkPassword(password, length, strength)) {
            int index = random.nextInt(DIGIT_END - DIGIT_START + 1);
            b.append(PASS_CHARS.charAt(index));

            index = random.nextInt(ALPHA_LOWER_END - ALPHA_LOWER_START + 1);
            b.append(PASS_CHARS.charAt(ALPHA_LOWER_START + index));

            index = random.nextInt(ALPHA_CAPS_END - ALPHA_CAPS_START + 1);
            b.append(PASS_CHARS.charAt(ALPHA_CAPS_START + index));

            if (strength == PASS_STRENGTH_SPECIAL) {
                index = random.nextInt(ALPHA_CHAR_END - ALPHA_CHAR_START + 1);
                b.append(PASS_CHARS.charAt(ALPHA_CHAR_START + index));
            }
        } else {
            for (int n = 0; n < 3; n++) {
                int index = random.nextInt(charIntervalEnd + 1);
                b.append(PASS_CHARS.charAt(index));
            }
        }

        return b.toString();
    }

    public String generateToken() {
        StringBuilder b = new StringBuilder();
        for (int n = 0; n < 20; n++) {
            b.append(PASS_CHARS.charAt(random.nextInt(ALPHA_CAPS_END)));
        }
        return b.toString();
    }
}
