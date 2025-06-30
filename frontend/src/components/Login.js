import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styles from './Login.module.css';

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    role: '',
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

    if (!formData.role) {
      newErrors.role = '로그인 유형을 선택해주세요.';
    }

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
      
      // 역할별 임시 계정 정보
      const demoAccounts = {
        author: {
          email: 'author@kt.com',
          password: 'password',
          data: {
            id: 1,
            name: '김작가',
            email: 'author@kt.com',
            role: 'author',
            isApproved: true,
            introduction: '소설과 에세이를 주로 집필하는 작가입니다.'
          }
        },
        customer: {
          email: 'customer@kt.com',
          password: 'password',
          data: {
            id: 2,
            name: '이고객',
            email: 'customer@kt.com',
            role: 'customer',
            subscription: {
              isValid: true,
              plan: 'premium'
            },
            points: 1500
          }
        }
      };

      const selectedAccount = demoAccounts[formData.role];
      
      if (selectedAccount && 
          formData.email === selectedAccount.email && 
          formData.password === selectedAccount.password) {
        userData = selectedAccount.data;
      } else {
        throw new Error(`${getRoleDisplayName(formData.role)} 계정 정보가 일치하지 않습니다.`);
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

  const getRoleDisplayName = (role) => {
    const roleNames = {
      author: '작가',
      customer: '고객'
    };
    return roleNames[role] || role;
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
          {/* 역할 선택 섹션 */}
          <div className={styles.formGroup}>
            <label className={styles.formLabel}>
              <span className="material-icons">account_circle</span>
              로그인 유형
            </label>
            <div className={styles.roleSelection}>
              <div 
                className={`${styles.roleOption} ${formData.role === 'customer' ? styles.selected : ''}`}
                onClick={() => handleChange({ target: { name: 'role', value: 'customer' } })}
              >
                <input
                  type="radio"
                  name="role"
                  value="customer"
                  checked={formData.role === 'customer'}
                  onChange={handleChange}
                  className={styles.roleInput}
                />
                <div className={styles.roleContent}>
                  <span className="material-icons">person</span>
                  <span>고객</span>
                </div>
              </div>
              
              <div 
                className={`${styles.roleOption} ${formData.role === 'author' ? styles.selected : ''}`}
                onClick={() => handleChange({ target: { name: 'role', value: 'author' } })}
              >
                <input
                  type="radio"
                  name="role"
                  value="author"
                  checked={formData.role === 'author'}
                  onChange={handleChange}
                  className={styles.roleInput}
                />
                <div className={styles.roleContent}>
                  <span className="material-icons">edit</span>
                  <span>작가</span>
                </div>
              </div>
            </div>
            {errors.role && <span className={styles.errorMessage}>{errors.role}</span>}
          </div>

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
                {formData.role ? `${getRoleDisplayName(formData.role)}으로 로그인` : '로그인'}
              </>
            )}
          </button>
        </form>

        <div className={styles.loginDemo}>
          <p className={styles.demoTitle}>데모 계정:</p>
          <div className={styles.demoAccounts}>
            <div className={styles.demoAccount}>
              <strong>👤 고객:</strong> customer@kt.com / password
            </div>
            <div className={styles.demoAccount}>
              <strong>✏️ 작가:</strong> author@kt.com / password
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