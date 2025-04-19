import { Button, Card } from "react-bootstrap";
import ImageSlider from "./layout/carousel";
import { FaHome, FaUsers, FaBell, FaCog } from "react-icons/fa";

const Home = () => {
  return (
    <>
      <ImageSlider />
      <div className="container mt-4">
        <div className="d-flex justify-content-around mt-4 px-3">
          <Button
            variant="outline-primary"
            className="d-flex flex-column align-items-center"
          >
            <FaHome size={24} />
            <span className="mt-1">Trang chủ</span>
          </Button>

          <Button
            variant="outline-success"
            className="d-flex flex-column align-items-center"
          >
            <FaUsers size={24} />
            <span className="mt-1">Cư dân</span>
          </Button>

          <Button
            variant="outline-warning"
            className="d-flex flex-column align-items-center"
          >
            <FaBell size={24} />
            <span className="mt-1">Thông báo</span>
          </Button>

          <Button
            variant="outline-secondary"
            className="d-flex flex-column align-items-center"
          >
            <FaCog size={24} />
            <span className="mt-1">Cài đặt</span>
          </Button>
        </div>
      </div>
      <Card className="text-center mt-2 shadow-sm border-0">
        <Card.Body>
          <h5 className="mb-3">Liên hệ & Hỗ trợ</h5>
          <Card.Text>
            Cần hỗ trợ hoặc có thắc mắc? Vui lòng liên hệ với ban quản lý tòa
            nhà để được trợ giúp nhanh nhất.
          </Card.Text>
          <Button variant="primary">Liên hệ ngay</Button>
        </Card.Body>
      </Card>
    </>
  );
};
export default Home;
