import React, { useState, useContext } from "react";
import { Button, Alert, Form } from "react-bootstrap";
import { authApis } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { useNavigate } from "react-router-dom";

const UploadAvatar = () => {
  const [avatar, setAvatar] = useState(null);
  const [msg, setMsg] = useState(null);
  const [variant, setVariant] = useState("success");

  const { user } = useContext(MyUserContext);
  console.log(user);
  const navigate = useNavigate();

  if (!user) {
    navigate("/login");
    return null;
  }

  const handleChangeAvatar = async (e) => {
    e.preventDefault();

    if (!avatar) {
      setMsg("Vui lòng chọn ảnh.");
      setVariant("warning");
      return;
    }

    const formData = new FormData();
    formData.append("avatar", avatar);

    try {
      const res = await authApis().put(`/users/${user.id}/update_avatar`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setMsg(res.data.message || "Cập nhật avatar thành công!");
      setVariant("success");
    } catch (err) {
      console.error("Upload error:", err);
      setMsg(err.response?.data?.error || "Cập nhật thất bại.");
      setVariant("danger");
    }
  };

  return (
    <Form onSubmit={handleChangeAvatar}>
      {msg && <Alert variant={variant}>{msg}</Alert>}

      <Form.Group className="mb-3">
        <Form.Label>Chọn ảnh đại diện</Form.Label>
        <Form.Control
          type="file"
          accept="image/*"
          onChange={(e) => setAvatar(e.target.files[0])}
        />
      </Form.Group>

      <Button type="submit">Cập nhật avatar</Button>
    </Form>
  );
};

export default UploadAvatar;
