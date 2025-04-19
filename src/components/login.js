import "./styles/login.css";
import { FaFacebook, FaGoogle } from "react-icons/fa";

const Login = () => {
  return (
    <div>
      <form className="glass-form">
        <h3>Đăng nhập</h3>

        <label htmlFor="username">Tên đăng nhập</label>
        <input type="text" placeholder="Email hoặc số điện thoại" id="username" />

        <label htmlFor="password">Mật khẩu</label>
        <input type="password" placeholder="Nhập mật khẩu" id="password" />

        <button>Đăng nhập</button>
        <div className="social">
          <div className="go">
            <FaGoogle /> Google
          </div>
          <div className="fb">
            <FaFacebook /> Facebook
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
