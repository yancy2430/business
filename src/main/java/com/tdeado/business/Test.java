package com.tdeado.business;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.security.KeyPair;

public class Test {
    public static void main(String[] args) {
//        KeyPair pair = SecureUtil.generateKeyPair("RSA");
//        System.err.println(Base64.encode(pair.getPrivate().getEncoded()));
//        System.err.println(Base64.encode(pair.getPublic().getEncoded()));
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA.getValue(),
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJdDyZap8WTnkUV9F32KUNgGpDuvhkntEm7bKkboChSaDbASOfbuHFQZAboLlTGV+Ee9+L7nKhCmiig5F+eb3vPUj0a41g4ozTZ6cjKEd58vuDuuaujkyqRBCRKVvGbCCN9JZHdb5HL1skt0S3DxE7IXqJPMGwRh5nyJNwjKHwx1AgMBAAECgYAUsIzuMqh16MaR/p4r6bNNGPt1vnhbvDV9PDT4JdzbHIcqwhNzlvYA+rqlu9fYRG8FQRK46d1zEZRsFAMVBtjjwtJM9oqGsCGkz4mEGfCEOPSrcI5WW9AtCAFabf8j6PaJKdIS1Sd6bZ58LPbshp0yjl6uHKAb4UwuVcQ3pnDRgQJBANu4nOymUGZ4C9ZRqnfMUsjU7WRsqZBkm1QZCbe7ytyHg08Or4ivZQAAY5nhGK6ysFsWHQhfSV8n4itWKNlgVQ0CQQCwPZhLr+CjmBTJ31BRiPdehfb6jJ1lzdmgj40OKiLOBHCk6MgwldLBPDFSYzg6ykw64WLoVtjJKyLvkatuRosJAkAU4wnX3T+LXiIR75V66et8t/ERONstpMK3TeIEm09/g0pSO50oKzQ4udC+Eg3cnZPyNLnxz80TxKDVac1cTLIJAkANoaX+7KqWXLP27UPcGpjvgbMZq/icoDBd/9xsJQwuHR/NUYfyYhQ8B/jUo4H84hrFTxuEVvtLi/+WwhCdZe1pAkEAjLUtT0RMjkQGMt0iQVPzGGtGybvEw55QUFOMP/Lixq19y07bz/sLycd9/1h7Jq2tghSnACut5i4zjQWNZH6OTQ==",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXQ8mWqfFk55FFfRd9ilDYBqQ7r4ZJ7RJu2ypG6AoUmg2wEjn27hxUGQG6C5UxlfhHvfi+5yoQpoooORfnm97z1I9GuNYOKM02enIyhHefL7g7rmro5MqkQQkSlbxmwgjfSWR3W+Ry9bJLdEtw8ROyF6iTzBsEYeZ8iTcIyh8MdQIDAQAB");

        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.err.println(Base64.encode(encrypt));
        String e = "iTVl/Oc4nwppYqXnL7rcRj2sTpprB+T+7Z+9lZkFcGwgDFSq/gl31P0cWbv5tX3tU9U8ASTJAqiRnn/h31r3N7dTZq6wcX4BYcjb4E4oYw5mYV0x7Uzf4NbtEnimeZEbecX5vKNuo0g2uTes93x60vkCeZE/gq0o1ftB1wsvmcE=";
        String a = "iTVl/Oc4nwppYqXnL7rcRj2sTpprB+T+7Z+9lZkFcGwgDFSq/gl31P0cWbv5tX3tU9U8ASTJAqiRnn/h31r3N7dTZq6wcX4BYcjb4E4oYw5mYV0x7Uzf4NbtEnimeZEbecX5vKNuo0g2uTes93x60vkCeZE/gq0o1ftB1wsvmcE=";
        byte[] decrypt = rsa.decrypt(e, KeyType.PrivateKey);
        System.err.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

    }
}
