package com.demo.web;

import com.demo.pojo.User;
import com.demo.service.IuserService;
import com.demo.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * author Jayce
 * create 2020-05-16 8:22
 */
public class LoginServlet extends HttpServlet {
  private IuserService service = new UserServiceImpl();
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    Integer userType = Integer.parseInt(req.getParameter("userType"));
    System.out.println(username +"+"+password+"+"+userType);
/*根据用户名密码数据库查询*/
    User user = service.getUser(username,password,userType);
    System.out.println(user);
    /**/
    if (user==null){
      String message=" <script>\n" +
          "          window.alert(\"输入有误，请重新输入\");\n" +
          "\n" +
          "      </script>";



      req.setAttribute("mess",message);
      req.getRequestDispatcher("index.jsp").forward(req,resp);

    }else {
//      user_type 管理员
//      del_mark 该用户不存在

      if(user.getDelMark() == 0)
      {
        String message=" <script>\n" +
            "          window.alert(\"用户信息已失效，请联系管理员\");\n" +
            "\n" +
            "      </script>";

        req.setAttribute("mess",message);
        req.getRequestDispatcher("index.jsp").forward(req,resp);

      }
      if(user.getUserType()==1){
        String message=" <script>\n" +
            "          window.alert(\"尊敬的管理员用户，登录成功！\");\n" +
            "\n" +
            "      </script>";

        req.setAttribute("mess",message);


      }
      req.setAttribute("user",user);
      HttpSession session = req.getSession();
      session.setAttribute("sessionuser",user);
      req.getRequestDispatcher("main.jsp").forward(req,resp);

    }

  }
}
