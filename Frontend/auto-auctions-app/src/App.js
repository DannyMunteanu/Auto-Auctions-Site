import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import AuthenticationPage from './pages/AuthenticationPage';
import Navbar from './components/additional/Navbar';
import ListingsPage from './pages/ListingsPage';
import ViewListingPage from './pages/ViewListingPage';

function App() {
  return (
    <div className="App">
      <Navbar/>
      <Router>
        <Routes>
          <Route path="/" element={<ListingsPage/>}/>
          <Route path="/listing/:registration" element={<ViewListingPage/>}/>
          <Route path="/auth" element={<AuthenticationPage/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
