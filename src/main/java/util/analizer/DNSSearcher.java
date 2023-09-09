package util.analizer;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;

public class DNSSearcher {
    public static void query(String domain) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put(PROVIDER_URL, "dns://8.8.8.8");
        String[] records = { "A" };
        DirContext ctx = new InitialDirContext(env);
        var attributes = ctx.getAttributes(domain, records).getAll();
        while (attributes.hasMore()) {
            Attribute a = attributes.next();
            System.out.println(a.getID());
            var values = a.getAll();
            while (values.hasMore()) {
                System.out.println(values.next());
            }
            System.out.println();
        }
        attributes.close();
    }
}
