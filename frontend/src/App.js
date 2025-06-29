import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import './App.css';

// 임시 페이지 컴포넌트들
const HomePage = () => (
  <div style={{ padding: '2rem' }}>
    <h1>KT Library 홈페이지</h1>
    <p>도서 출판 및 구독 플랫폼에 오신 것을 환영합니다!</p>
    
    {/* 테스트용 로그인 버튼들 */}
    <div style={{ marginTop: '2rem', display: 'flex', gap: '1rem' }}>
      <button 
        onClick={() => {
          localStorage.setItem('user', JSON.stringify({
            id: 1,
            name: '김작가',
            email: 'author@example.com',
            role: 'author'
          }));
          window.location.reload();
        }}
        style={{ padding: '0.5rem 1rem', backgroundColor: '#059669', color: 'white', border: 'none', borderRadius: '0.25rem' }}
      >
        작가로 로그인
      </button>
      
      <button 
        onClick={() => {
          localStorage.setItem('user', JSON.stringify({
            id: 2,
            name: '이고객',
            email: 'customer@example.com',
            role: 'customer'
          }));
          window.location.reload();
        }}
        style={{ padding: '0.5rem 1rem', backgroundColor: '#3b82f6', color: 'white', border: 'none', borderRadius: '0.25rem' }}
      >
        고객으로 로그인
      </button>
      
      <button 
        onClick={() => {
          localStorage.setItem('user', JSON.stringify({
            id: 3,
            name: '박관리자',
            email: 'admin@example.com',
            role: 'admin'
          }));
          window.location.reload();
        }}
        style={{ padding: '0.5rem 1rem', backgroundColor: '#dc2626', color: 'white', border: 'none', borderRadius: '0.25rem' }}
      >
        관리자로 로그인
      </button>
    </div>
  </div>
);

const LoginPage = () => (
  <div style={{ padding: '2rem' }}>
    <h1>로그인</h1>
    <p>로그인 페이지입니다.</p>
  </div>
);

const RegisterPage = () => (
  <div style={{ padding: '2rem' }}>
    <h1>회원가입</h1>
    <p>회원가입 페이지입니다.</p>
  </div>
);

// 작가 페이지들
const AuthorManuscripts = () => (
  <div style={{ padding: '2rem' }}>
    <h1>내 원고</h1>
    <p>작가의 원고 목록입니다.</p>
  </div>
);

const AuthorNewManuscript = () => (
  <div style={{ padding: '2rem' }}>
    <h1>새 원고</h1>
    <p>새 원고를 작성하는 페이지입니다.</p>
  </div>
);

const AuthorPublishing = () => (
  <div style={{ padding: '2rem' }}>
    <h1>출판 현황</h1>
    <p>출판 현황을 확인하는 페이지입니다.</p>
  </div>
);

const AuthorProfile = () => (
  <div style={{ padding: '2rem' }}>
    <h1>작가 정보</h1>
    <p>작가 정보를 관리하는 페이지입니다.</p>
  </div>
);

// 고객 페이지들
const CustomerBooks = () => (
  <div style={{ padding: '2rem' }}>
    <h1>도서 둘러보기</h1>
    <p>도서를 둘러보는 페이지입니다.</p>
  </div>
);

const CustomerBookshelf = () => (
  <div style={{ padding: '2rem' }}>
    <h1>내 서재</h1>
    <p>내 서재 페이지입니다.</p>
  </div>
);

const CustomerSubscription = () => (
  <div style={{ padding: '2rem' }}>
    <h1>구독 관리</h1>
    <p>구독 관리 페이지입니다.</p>
  </div>
);

const CustomerPoints = () => (
  <div style={{ padding: '2rem' }}>
    <h1>포인트</h1>
    <p>포인트 관리 페이지입니다.</p>
  </div>
);

// 관리자 페이지들
const AdminAuthors = () => (
  <div style={{ padding: '2rem' }}>
    <h1>작가 승인</h1>
    <p>작가 승인 관리 페이지입니다.</p>
  </div>
);

const AdminBooks = () => (
  <div style={{ padding: '2rem' }}>
    <h1>도서 관리</h1>
    <p>도서 관리 페이지입니다.</p>
  </div>
);

const AdminCustomers = () => (
  <div style={{ padding: '2rem' }}>
    <h1>고객 관리</h1>
    <p>고객 관리 페이지입니다.</p>
  </div>
);

function App() {
  return (
    <Router>
      <div className="App">
        <Navigation />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          
          {/* 작가 라우트 */}
          <Route path="/author/manuscripts" element={<AuthorManuscripts />} />
          <Route path="/author/new-manuscript" element={<AuthorNewManuscript />} />
          <Route path="/author/publishing" element={<AuthorPublishing />} />
          <Route path="/author/profile" element={<AuthorProfile />} />
          
          {/* 고객 라우트 */}
          <Route path="/customer/books" element={<CustomerBooks />} />
          <Route path="/customer/bookshelf" element={<CustomerBookshelf />} />
          <Route path="/customer/subscription" element={<CustomerSubscription />} />
          <Route path="/customer/points" element={<CustomerPoints />} />
          
          {/* 관리자 라우트 */}
          <Route path="/admin/authors" element={<AdminAuthors />} />
          <Route path="/admin/books" element={<AdminBooks />} />
          <Route path="/admin/customers" element={<AdminCustomers />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;