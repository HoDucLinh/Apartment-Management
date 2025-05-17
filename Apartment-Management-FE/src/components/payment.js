import React, { useState, useEffect } from 'react';
import { Card, Button, Row, Col, Alert, Spinner } from 'react-bootstrap';
import './styles/payment.css';
import { FaMoneyBillWave, FaQrcode } from 'react-icons/fa';
import Apis, { endpoints } from '../configs/Apis';

const Payment = () => {
  const [invoices, setInvoices] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [paymentMethod, setPaymentMethod] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const [orderUrl, setOrderUrl] = useState('');
  const [loading, setLoading] = useState(true);

  // ✅ Load danh sách hóa đơn từ API khi mở component
  useEffect(() => {
    const loadInvoices = async () => {
      try {
        const res = await Apis.get(endpoints['invoices']);
        setInvoices(res.data); // Assumes res.data is an array of invoices
      } catch (error) {
        console.error('Lỗi khi tải danh sách hóa đơn:', error);
      } finally {
        setLoading(false);
      }
    };

    loadInvoices();
  }, []);

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

    const selectedInvoices = invoices.filter(inv => selectedItems.includes(inv.id));
    const totalAmount = selectedInvoices.reduce((sum, inv) => sum + inv.amount, 0);

    try {
      if (paymentMethod === 'zalopay') {
        const res = await Apis.post(endpoints['payment'], {
          items: selectedItems, // gửi danh sách invoice ID
          amount: totalAmount,  // tổng số tiền
        });
        console.log('Đơn hàng ZaloPay:', res.data);
        setOrderUrl(res.data);
        window.open(res.data, '_blank');
      } else {
        alert('Thanh toán trực tiếp đã được chọn.');
      }
    } catch (error) {
      console.error('Lỗi khi tạo đơn hàng ZaloPay:', error);
      alert('Có lỗi xảy ra trong quá trình thanh toán.');
    }
  };

  const totalAmount = invoices
    .filter((item) => selectedItems.includes(item.id))
    .reduce((sum, item) => sum + item.amount, 0);

  return (
    <div className="payment-container">
      <h2 className="text-center mb-4">Thanh toán</h2>
      {showAlert && (
        <Alert variant="danger">Vui lòng chọn phương thức và mục thanh toán.</Alert>
      )}

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" />
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <Row className="mb-4">
            {invoices.map((item) => (
              <Col md={4} key={item.id}>
                <Card
                  className={`payment-card ${selectedItems.includes(item.id) ? 'selected' : ''}`}
                  onClick={() => handleItemClick(item.id)}
                >
                  <Card.Body className="text-center">
                    <Card.Title>{item.description || `Hóa đơn #${item.id}`}</Card.Title>
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
      )}
    </div>
  );
};

export default Payment;
