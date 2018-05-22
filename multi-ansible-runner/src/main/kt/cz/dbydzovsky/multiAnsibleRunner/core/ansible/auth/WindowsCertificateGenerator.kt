package cz.dbydzovsky.multiAnsibleRunner.core.ansible.auth

import cz.dbydzovsky.multiAnsibleRunner.core.ansible.obj.AnsibleRun

class WindowsCertificateGenerator: AnsibleAuthentication {

    override fun authenticate(ansibleRun: AnsibleRun) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

/*

    def USERNAME = System.getenv("username")

    @Override
    cz.dbydzovsky.core.AnsibleRun authenticate(cz.dbydzovsky.core.AnsibleRun ansibleRun) {
        generateKey()
        ansibleRun
    }

//    void generateKey() {
////        https://www.pixelstech.net/article/1406726666-Generate-certificate-in-Java----Certificate-chain
////        def command = ["\"$JAVA_HOME\\bin\\keytool\" -genkey -alias scaler -dname \"cn=$USERNAME, ou=Scaler, o=Quadient, c=CZ\" -keystore \".\\.keystore\" -keyalg RSA -storepass scaler -validity 180 -keypass scaler"]
////        CommandExecutor.execute(command)
////        new File(".keystore")
//
//        KeyPairGenerator
//        CertAndKeyGen keyGen=new CertAndKeyGen("RSA","SHA1WithRSA",null)
//        keyGen.generate(1024)
//        PrivateKey privateKey = keyGen.privateKey
//
//        //Generate self signed certificate
//        X509Certificate[] chain=new X509Certificate[1]
//        chain[0]=keyGen.getSelfCertificate(new X500Name("cn=$USERNAME, ou=Scaler, o=Quadient, c=CZ"), (long)365*24*3600)
//        PublicKey publicKey = chain[0].publicKey
//
//
//        System.out.println("Certificate : "+chain[0].toString())
//        System.out.println("publicKey : "+publicKey.toString())
//        System.out.println("privateKey : "+privateKey.encoded)
//    }
    public static void generateKey() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA")
            keyGen.initialize(2048);
            final KeyPair key = keyGen.generateKeyPair();

            byte[] encodedPublicKey = key.getPublic().encoded
            byte[] encodedPrivateKey = key.getPrivate().encoded
            String b64PublicKey = Base64.getEncoder().encodeToString("-----BEGIN CERTIFICATE-----\n".bytes+encodedPublicKey+"\n-----END CERTIFICATE-----".bytes);
            String b64PrivateKey = Base64.getEncoder().encodeToString("-----BEGIN PUBLIC KEY-----\n".bytes + encodedPrivateKey + "\n-----BEGIN PUBLIC KEY-----".bytes);

            File privateKeyFile = new File("D:\\temp\\cert\\private");
            File publicKeyFile = new File("D:\\temp\\cert\\public.pem");

            [new Tuple2(privateKeyFile, b64PrivateKey), new Tuple2(publicKeyFile, b64PublicKey)].forEach{
                OutputStreamWriter publicKeyWriter =
                new OutputStreamWriter(
                        new FileOutputStream(it.first),
                StandardCharsets.US_ASCII.newEncoder())
                try {
                    publicKeyWriter.write(it.second);
                } finally {
                    publicKeyWriter.close()
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 */