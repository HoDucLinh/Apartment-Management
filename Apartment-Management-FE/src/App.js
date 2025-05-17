import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Sidebar from './components/layout/sidebar';
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from './components/layout/footer';
import Login from './components/login';
import Payment from './components/payment';
import MyUserReducer from "./reducers/MyUserReducer";
import { useReducer } from 'react';
import { MyDispatcherContext, MyUserContext } from './configs/MyContexts';



const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <MyUserContext.Provider value={user}>
      <MyDispatcherContext.Provider value={dispatch}>
      <BrowserRouter>
        <Sidebar />
          <Routes> 
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/payment" element={<Payment />} />
          </Routes>
        <Footer />
      </BrowserRouter>
    </MyDispatcherContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;