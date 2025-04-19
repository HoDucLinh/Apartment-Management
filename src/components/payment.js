import React, { useState } from 'react';
import { Card, Button, Row, Col, Alert, Image } from 'react-bootstrap';
import './styles/payment.css';
import { FaHome, FaTools, FaBolt, FaMoneyBillWave, FaQrcode } from 'react-icons/fa';

const Payment = () => {
  const [selectedItems, setSelectedItems] = useState([]);
  const [paymentMethod, setPaymentMethod] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const [qrCodeUrl, setQrCodeUrl] = useState('');

  const paymentItems = [
    { id: 'apartment', name: 'Tiền căn hộ', amount: 3000000, icon: <FaHome /> },
    { id: 'management', name: 'Tiền quản lý', amount: 500000, icon: <FaTools /> },
    { id: 'utilities', name: 'Tiền điện nước', amount: 800000, icon: <FaBolt /> },
  ];

  const handleItemClick = (id) => {
    setSelectedItems((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  const handleMethodChange = (method) => {
    setPaymentMethod(method);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!paymentMethod || selectedItems.length === 0) {
      setShowAlert(true);
      return;
    }

    setShowAlert(false);

    if (paymentMethod === 'zalopay') {
      // Gửi yêu cầu tạo đơn hàng đến server để nhận URL mã QR
      try {
        const response = await fetch('/api/create-zalopay-order', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            items: selectedItems,
            amount: totalAmount,
          }),
        });

        const data = await response.json();
        setQrCodeUrl(data.qrCodeUrl);
      } catch (error) {
        console.error('Lỗi khi tạo đơn hàng ZaloPay:', error);
      }
    } else {
      // Xử lý thanh toán trực tiếp
      alert('Thanh toán trực tiếp đã được chọn.');
    }
  };

  const totalAmount = paymentItems
    .filter((item) => selectedItems.includes(item.id))
    .reduce((sum, item) => sum + item.amount, 0);

  return (
    <div className="payment-container">
      <h2 className="text-center mb-4">Thanh toán</h2>
      {showAlert && (
        <Alert variant="danger">Vui lòng chọn phương thức và mục thanh toán.</Alert>
      )}
      <form onSubmit={handleSubmit}>
        <Row className="mb-4">
          {paymentItems.map((item) => (
            <Col md={4} key={item.id}>
              <Card
                className={`payment-card ${selectedItems.includes(item.id) ? 'selected' : ''}`}
                onClick={() => handleItemClick(item.id)}
              >
                <Card.Body className="text-center">
                  <div className="icon">{item.icon}</div>
                  <Card.Title>{item.name}</Card.Title>
                  <Card.Text>{item.amount.toLocaleString()} VND</Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>

        <h5>Phương thức thanh toán:</h5>
        <Row className="mb-4">
          <Col md={6}>
            <Card
              className={`payment-method-card ${paymentMethod === 'direct' ? 'selected' : ''}`}
              onClick={() => handleMethodChange('direct')}
            >
              <Card.Body className="text-center">
                <div className="icon">
                  <FaMoneyBillWave />
                </div>
                <Card.Title>Trực tiếp</Card.Title>
              </Card.Body>
            </Card>
          </Col>
          <Col md={6}>
            <Card
              className={`payment-method-card ${paymentMethod === 'zalopay' ? 'selected' : ''}`}
              onClick={() => handleMethodChange('zalopay')}
            >
              <Card.Body className="text-center">
                <div className="icon">
                  <FaQrcode />
                </div>
                <Card.Title>ZaloPay</Card.Title>
              </Card.Body>
            </Card>
          </Col>
        </Row>

        <h5>Tổng cộng: {totalAmount.toLocaleString()} VND</h5>

        <Button variant="primary" type="submit" className="mt-3 w-100">
          Thanh toán
        </Button>
      </form>

      {qrCodeUrl && (
        <div className="text-center mt-4">
          <h5>Quét mã QR bằng ZaloPay để thanh toán:</h5>
          <Image src={qrCodeUrl} alt="Mã QR ZaloPay" fluid />
        </div>
      )}
    </div>
  );
};

export default Payment;
