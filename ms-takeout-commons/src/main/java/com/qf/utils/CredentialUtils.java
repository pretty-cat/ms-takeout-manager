package com.qf.utils;

import org.springframework.util.DigestUtils;

/**
 * 密码加盐处理
 */
public class CredentialUtils {

    // 自定义异常
    public class SaltInvalidNumberException extends RuntimeException {
        public SaltInvalidNumberException(String msg) {
            super(msg);
        }
    }

    /**
     *  加盐处理。目的是为了应对彩虹表。
     *      1.两次md5
     *
     *  20 2c b9 62 ac 59 075b964b07152d234b70
     *  将密码md5加密后，长度为32， 再生成16个随机字符串,每两位中间插一个字符。
     *  形成了48位长度的字符串，在做md5加密。
     *
     *  数据库存储的是：加盐后的md5字符串，还有盐值。
     * @param password
     * @return
     */
    public static String cryptPassword(String password, String saltValue) {

        //将原密码通过md5加密，长度为32

        String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
//        System.out.println(md5Str);
        char[] pwdMd5 = md5Str.toCharArray();
        char[] saltCharArr = saltValue.toCharArray();


        if(saltCharArr.length != 16) {
            throw new CredentialUtils().new SaltInvalidNumberException("盐值长度必须为16位");
        }

        char[] newPwdArray = new char[48];

        /**
         * 01 23 45
         * 202cb962ac59075b964b07152d234b70
         * bsrutzvlgjynznqr
         */
        for (int i = 0; i < 16; i++) {
            newPwdArray[i*3] = pwdMd5[i*2];
            newPwdArray[i*3 + 1] = pwdMd5[i*2 + 1];
            newPwdArray[i*3 + 2] = saltCharArr[i];
        }

        return DigestUtils.md5DigestAsHex(new String(newPwdArray).getBytes());
    }



    public static void main(String[] args) {
        /**
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();

        String randomString = generator.generate(16);

        System.out.println(randomString); //kxjmzthsrpeacltp

        System.out.println(cryptPassword("123", randomString.toCharArray()));
         */
        String one = DigestUtils.md5DigestAsHex("123".getBytes());
//        System.out.println(one);
//
        System.out.println(DigestUtils.md5DigestAsHex(one.getBytes()));


        /**
        String salt = "rtmrsskrqdappjdp";
        System.out.println(cryptPassword("123", salt));
         */
        //  d022646351048ac0ba397d12dfafa304
//        System.out.println(new SimpleHash("md5", "123", null, 2).toString());


        // 123 -> 通过两次md5的到的数据  d9b1d7db4cd6e70935368a1efb10e377

    }
}
