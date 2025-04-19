import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Sidebar from './components/layout/sidebar';
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from './components/layout/footer';
import Login from './components/login';
import Payment from './components/payment';

function App() {
  return (
    <div className="app-container">
      <BrowserRouter>
        <Sidebar />
        <div className="content-wrap">
          <Routes> 
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/payment" element={<Payment />} />
          </Routes>
        </div>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;
