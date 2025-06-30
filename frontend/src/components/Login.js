import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styles from './Login.module.css';

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    
    // 에러 메시지 클리어
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.email.trim()) {
      newErrors.email = '이메일을 입력해주세요.';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = '올바른 이메일 형식을 입력해주세요.';
    }

    if (!formData.password.trim()) {
      newErrors.password = '비밀번호를 입력해주세요.';
    } else if (formData.password.length < 6) {
      newErrors.password = '비밀번호는 6자 이상이어야 합니다.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setIsLoading(true);

    try {
      // TODO: 실제 API 호출로 교체
      // const response = await fetch('/api/auth/login', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify(formData),
      // });

      // 임시 로그인 로직 (데모용)
      await new Promise(resolve => setTimeout(resolve, 1000)); // 로딩 시뮬레이션

      let userData;
      
      // 임시 계정 정보
      if (formData.email === 'author@kt.com' && formData.password === 'password') {
        userData = {
          id: 1,
          name: '김작가',
          email: 'author@kt.com',
          role: 'author',
          isApproved: true
        };
      } else if (formData.email === 'customer@kt.com' && formData.password === 'password') {
        userData = {
          id: 2,
          name: '이고객',
          email: 'customer@kt.com',
          role: 'customer',
          subscription: {
            isValid: true,
            plan: 'premium'
          }
        };
      } else if (formData.email === 'admin@kt.com' && formData.password === 'password') {
        userData = {
          id: 3,
          name: '박관리자',
          email: 'admin@kt.com',
          role: 'admin'
        };
      } else {
        throw new Error('이메일 또는 비밀번호가 잘못되었습니다.');
      }

      // 로컬 스토리지에 사용자 정보 저장
      localStorage.setItem('user', JSON.stringify(userData));
      localStorage.setItem('token', 'demo_token_' + Date.now());

      // 역할에 따른 리다이렉트
      switch (userData.role) {
        case 'author':
          navigate('/author/manuscripts');
          break;
        case 'customer':
          navigate('/customer/books');
          break;
        case 'admin':
          navigate('/admin/authors');
          break;
        default:
          navigate('/');
      }

      // 페이지 새로고침으로 네비게이션 업데이트
      window.location.reload();
      
    } catch (error) {
      setErrors({
        submit: error.message || '로그인 중 오류가 발생했습니다.'
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={styles.loginContainer}>
      <div className={styles.loginCard}>
        <div className={styles.loginHeader}>
          <div className={styles.loginLogo}>
            <span className="material-icons">menu_book</span>
            <h1>KT Library</h1>
          </div>
          <p className={styles.loginSubtitle}>도서 출판 및 구독 플랫폼에 로그인하세요</p>
        </div>

        <form className={styles.loginForm} onSubmit={handleSubmit}>
          <div className={styles.formGroup}>
            <label htmlFor="email" className={styles.formLabel}>
              <span className="material-icons">email</span>
              이메일
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className={`${styles.formInput} ${errors.email ? styles.error : ''}`}
              placeholder="이메일을 입력하세요"
              disabled={isLoading}
            />
            {errors.email && <span className={styles.errorMessage}>{errors.email}</span>}
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="password" className={styles.formLabel}>
              <span className="material-icons">lock</span>
              비밀번호
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className={`${styles.formInput} ${errors.password ? styles.error : ''}`}
              placeholder="비밀번호를 입력하세요"
              disabled={isLoading}
            />
            {errors.password && <span className={styles.errorMessage}>{errors.password}</span>}
          </div>

          {errors.submit && (
            <div className={`${styles.errorMessage} ${styles.submitError}`}>
              <span className="material-icons">error</span>
              {errors.submit}
            </div>
          )}

          <button 
            type="submit" 
            className={`${styles.loginButton} ${isLoading ? styles.loading : ''}`}
            disabled={isLoading}
          >
            {isLoading ? (
              <>
                <span className={styles.loadingSpinner}></span>
                로그인 중...
              </>
            ) : (
              <>
                <span className="material-icons">login</span>
                로그인
              </>
            )}
          </button>
        </form>

        <div className={styles.loginDemo}>
          <p className={styles.demoTitle}>데모 계정:</p>
          <div className={styles.demoAccounts}>
            <div className={styles.demoAccount}>
              <strong>작가:</strong> author@kt.com / password
            </div>
            <div className={styles.demoAccount}>
              <strong>고객:</strong> customer@kt.com / password
            </div>
            <div className={styles.demoAccount}>
              <strong>관리자:</strong> admin@kt.com / password
            </div>
          </div>
        </div>

        <div className={styles.loginFooter}>
          <p>
            계정이 없으신가요?{' '}
            <Link to="/register" className={styles.registerLink}>
              회원가입
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;