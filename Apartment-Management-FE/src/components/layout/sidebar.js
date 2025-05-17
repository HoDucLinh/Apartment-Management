import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext, MyDispatcherContext } from "../../configs/MyContexts";
import cookie from "react-cookies";

import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Offcanvas from "react-bootstrap/Offcanvas";
import Dropdown from "react-bootstrap/Dropdown";

function Sidebar() {
  const { user } = useContext(MyUserContext);
  const dispatch = useContext(MyDispatcherContext);
  const navigate = useNavigate();

  const logout = () => {
    cookie.remove("token");
    dispatch({ type: "logout" });
    navigate("/login");
  };

  const handleChangePassword = () => {
    navigate("/change-password"); // Đường dẫn trang đổi mật khẩu
  };

  const handleChangeAvatar = () => {
    navigate("/update-avatar"); // Đường dẫn trang đổi avatar
  };

  return (
    <Navbar expand="lg" className="bg-body-tertiary mb-3">
      <Container fluid>
        <Navbar.Brand href="#">Quản Lý Căn Hộ</Navbar.Brand>
        <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-lg`} />

        <Navbar.Offcanvas
          id={`offcanvasNavbar-expand-lg`}
          aria-labelledby={`offcanvasNavbarLabel-expand-lg`}
          placement="end"
        >
          <Offcanvas.Header closeButton>
            <Offcanvas.Title id={`offcanvasNavbarLabel-expand-lg`}>
              Chức năng
            </Offcanvas.Title>
          </Offcanvas.Header>
          <Offcanvas.Body>
            <Nav className="justify-content-end flex-grow-1 pe-3" style={{ alignItems: "center" }}>
              <Link to="/" className="nav-link">Trang chủ</Link>
              <Link to="/payment" className="nav-link">Thanh toán</Link>

              {user ? (
                <>
                  <Dropdown>
                    <Dropdown.Toggle
                      variant="link"
                      id="dropdown-user"
                      style={{ fontWeight: "bold", color: "#0d6efd", textDecoration: "none" }}
                    >
                      Xin chào, {user.username}
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                      <Dropdown.Item onClick={handleChangePassword}>
                        Đổi mật khẩu
                      </Dropdown.Item>
                      <Dropdown.Item onClick={handleChangeAvatar}>
                        Thay đổi avatar
                      </Dropdown.Item>
                      <Dropdown.Divider />
                      <Dropdown.Item onClick={logout} className="text-danger">
                        Đăng xuất
                      </Dropdown.Item>
                    </Dropdown.Menu>
                  </Dropdown>
                </>
              ) : (
                <Link to="/login" className="nav-link">Đăng nhập</Link>
              )}
            </Nav>
          </Offcanvas.Body>
        </Navbar.Offcanvas>
      </Container>
    </Navbar>
  );
}

export default Sidebar;
