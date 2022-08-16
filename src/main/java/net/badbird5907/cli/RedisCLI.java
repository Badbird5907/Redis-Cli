package net.badbird5907.cli;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Arrays;
import java.util.Scanner;

public class RedisCLI {
    public static void main(String[] args) {
        System.out.println("RedisCLI V." + RedisCLI.class.getPackage().getImplementationVersion());
        String host = "127.0.0.1";
        int port = 6379;
        //set host and port from args
        if (args.length == 1) {
            host = args[0];
        }
        if (args.length == 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        Jedis jedis = new Jedis(host, port);
        //if (!jedis.isConnected())
        //    throw new RuntimeException("Connection failed");
        while (true) {
            System.out.print(host + ":" + port + "> ");
            String in = scanner.nextLine();
            if (in.equalsIgnoreCase("exit")) {
                break;
            }
            String[] arg = in.split("\\s+");
            if (arg.length == 1) {
                runCommand(arg[0], new String[]{}, jedis);
            }
            else{
                String[] fArgs = new String[arg.length - 1];
                System.arraycopy(arg, 1, fArgs, 0, arg.length - 1);
                runCommand(arg[0], fArgs, jedis);
            }

        }
    }
    public static void runCommand(String command,String[] args, Jedis connection) {
        if (command.equalsIgnoreCase("help")) {
            System.out.println("Avaliable Commands:");
            for (Protocol.Command value : Protocol.Command.values()) {
                System.out.println(value.name());
            }
            return;
        }
        Protocol.Command cmd;
        try {
            cmd = Protocol.Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown command: " + command);
            return;
        }
        try {
            Protocol
            System.out.println(connection.sendBlockingCommand(cmd, args));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static class JedisConn extends Connection {

        public JedisConn(String host, int port) {

            super(host, port);
        }
        @Override
        public void sendCommand(ProtocolCommand cmd) {
            super.sendCommand(cmd);
        }
    }
}
