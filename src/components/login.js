import { Link } from "react-router-dom";
import "./styles/login.css";
import { FaGithub, FaGoogle } from "react-icons/fa";

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
          <Link to="/auth/google" className="go">
            <FaGoogle className="icon" /> Google
          </Link>
          <Link to="/auth/github" className="go">
            <FaGithub className="icon" /> GitHub
          </Link>
        </div>
      </form>
    </div>
  );
};

export default Login;
