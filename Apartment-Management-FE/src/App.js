import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Sidebar from './components/layout/sidebar';
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from './components/layout/footer';
import Login from './components/login';
import Payment from './components/payment';
import { AuthProvider } from './configs/MyContexts';
import ChangePassword from './components/changePassword';
import UploadAvatar from './components/uploadAvatar';

  return (
    <div className="app-container">
      <AuthProvider>
        <BrowserRouter>
        <Sidebar />
          <Routes> 
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/payment" element={<Payment />} />
            <Route path='/change-password' element={<ChangePassword/>} />
            <Route path='/update-avatar' element={<UploadAvatar/>} />
          </Routes>
        <Footer />
      </BrowserRouter>
      </AuthProvider>
      
    </div>
  );
}

export default App;