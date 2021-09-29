package into;

import into.dao.LiteraryFormatDao;
import into.dao.LiteraryFormatDaoImpl;
import into.models.LiteraryFormat;
import into.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LiteraryFormatDao literaryFormatDao = new LiteraryFormatDaoImpl();


        System.out.println(literaryFormatDao.delete(3L));
        literaryFormatDao.getAll();
        List<LiteraryFormat> all = literaryFormatDao.getAll();
        for (LiteraryFormat temp : all) {
            System.out.println(temp);
        }

    }
}
