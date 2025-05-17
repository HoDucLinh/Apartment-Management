import React, { useState, useEffect } from 'react'; // Thêm useEffect
import axios from 'axios';
import { Form, Button, Alert, Container, Row, Col, Card } from 'react-bootstrap';
import Apis, { authApis, endpoints } from '../configs/Apis';

function ChangePassword({ userId: propUserId, token }) {
    const [userId, setUserId] = useState(propUserId); // Sử dụng propUserId làm giá trị ban đầu
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [message, setMessage] = useState('');
    const [variant, setVariant] = useState('');

    useEffect(() => {
        const fetchUserId = async () => {
            if (!propUserId) { // Chỉ gọi API nếu propUserId không có
                try {
                    const api = authApis();
                    const response = await api.get(endpoints['current-user']);
                    setUserId(response.data.id); // Giả sử API trả về { id: 5, ... }
                } catch (error) {
                    console.error('Error fetching user ID:', error);
                    setMessage('Không thể lấy thông tin người dùng!');
                    setVariant('danger');
                }
            }
        };
        fetchUserId();
    }, [propUserId]); // Thêm propUserId vào dependency array

    const handleChangePassword = async (e) => {
        e.preventDefault();
        if (!userId) {
            setMessage('Không thể lấy thông tin người dùng!');
            setVariant('danger');
            return;
        }

        const formData = new FormData();
        formData.append('oldPassword', oldPassword);
        formData.append('newPassword', newPassword);
        const api = authApis();

        try {
            const response = await api.put(`/users/${userId}/change_password`, formData);
            setMessage(response.data.message || 'Đổi mật khẩu thành công!');
            setVariant('success');
            setOldPassword('');
            setNewPassword('');
        } catch (error) {
            setMessage(error.response?.data?.error || 'Đổi mật khẩu thất bại!');
            setVariant('danger');
        }
    };

    return (
        <Container className="d-flex justify-content-center mt-5">
            <Row className="w-100" style={{ maxWidth: '400px' }}>
                <Col>
                    <Card>
                        <Card.Body>
                            <Card.Title className="mb-4 text-center">Đổi mật khẩu</Card.Title>
                            {message && <Alert variant={variant}>{message}</Alert>}
                            <Form onSubmit={handleChangePassword}>
                                <Form.Group className="mb-3" controlId="oldPassword">
                                    <Form.Label>Mật khẩu cũ</Form.Label>
                                    <Form.Control
                                        type="password"
                                        placeholder="Nhập mật khẩu cũ"
                                        value={oldPassword}
                                        onChange={(e) => setOldPassword(e.target.value)}
                                        required
                                    />
                                </Form.Group>

                                <Form.Group className="mb-4" controlId="newPassword">
                                    <Form.Label>Mật khẩu mới</Form.Label>
                                    <Form.Control
                                        type="password"
                                        placeholder="Nhập mật khẩu mới"
                                        value={newPassword}
                                        onChange={(e) => setNewPassword(e.target.value)}
                                        required
                                    />
                                </Form.Group>

                                <Button variant="primary" type="submit" className="w-100">
                                    Xác nhận
                                </Button>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default ChangePassword;