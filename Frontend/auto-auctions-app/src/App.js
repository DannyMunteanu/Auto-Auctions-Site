import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import AuthenticationPage from './pages/AuthenticationPage';
import Navbar from './components/Navbar';
import ListingsPage from './pages/ListingsPage';

function App() {
  return (
    <div className="App">
      <Navbar/>
      <Router>
        <Routes>
          <Route path="/" element={<ListingsPage/>}/>
          <Route path="/auth" element={<AuthenticationPage/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
