package ru.bvn13.jproxy;

public class JProxy
{
    public static void main( String[] args )
    {
        ProxyServer server = new ProxyServer(8099, "192.168.199.200", 1122);

    }
}
