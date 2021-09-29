public class CipherToolTester{
    public static void main(String[] args){
        CipherTool cipherTool = new CipherTool();

        // Testing encryption
        // System.out.println(cipherTool.encrypt("Hello there!", null));


        
        // String to decrypt: FW+cAZUfyzU7jQwVZvxFUg==
        // Key: GGmW4qUNu7P/Pxrew73WkcKRoWyEdQXYtEPlszp3yrM=

        // Testing decryption
        System.out.println(cipherTool.decrypt("zqWlrZZRxzVo2UYIi5/fyOVnJNKVCD1Z3B+7Bj2ngL8=", "+CdIWyT7QeuCBG/LVckq7cowUJt9LxnECJeNXx12CnY="));
        // System.out.println(cipherTool.decrypt("KgzAInAjTh/IzeWXCha9gA==", "+CdIWyT7QeuCBG/LVckq7cowUJt9LxnECJeNXx12CnY="));
    }
}