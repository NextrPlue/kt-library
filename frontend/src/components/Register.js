import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styles from './Register.module.css';

const Register = () => {
  const navigate = useNavigate();
  const [currentStep, setCurrentStep] = useState(1);
  const [formData, setFormData] = useState({
    role: '',
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    introduction: '',
    agreeTerms: false,
    agreePrivacy: false
  });
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
    
    // 에러 메시지 클리어
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateStep1 = () => {
    const newErrors = {};

    if (!formData.role) {
      newErrors.role = '가입 유형을 선택해주세요.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const validateStep2 = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = '이름을 입력해주세요.';
    } else if (formData.name.length < 2) {
      newErrors.name = '이름은 2자 이상이어야 합니다.';
    }

    if (!formData.email.trim()) {
      newErrors.email = '이메일을 입력해주세요.';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = '올바른 이메일 형식을 입력해주세요.';
    }

    if (!formData.password.trim()) {
      newErrors.password = '비밀번호를 입력해주세요.';
    } else if (formData.password.length < 8) {
      newErrors.password = '비밀번호는 8자 이상이어야 합니다.';
    } else if (!/(?=.*[a-zA-Z])(?=.*\d)/.test(formData.password)) {
      newErrors.password = '비밀번호는 영문과 숫자를 포함해야 합니다.';
    }

    if (!formData.confirmPassword.trim()) {
      newErrors.confirmPassword = '비밀번호 확인을 입력해주세요.';
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    }

    if (formData.role === 'author' && !formData.introduction.trim()) {
      newErrors.introduction = '작가 소개를 입력해주세요.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const validateStep3 = () => {
    const newErrors = {};

    if (!formData.agreeTerms) {
      newErrors.agreeTerms = '이용약관에 동의해주세요.';
    }

    if (!formData.agreePrivacy) {
      newErrors.agreePrivacy = '개인정보처리방침에 동의해주세요.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleNext = () => {
    let isValid = false;
    
    switch (currentStep) {
      case 1:
        isValid = validateStep1();
        break;
      case 2:
        isValid = validateStep2();
        break;
      default:
        isValid = true;
    }

    if (isValid) {
      setCurrentStep(prev => prev + 1);
    }
  };

  const handlePrevious = () => {
    setCurrentStep(prev => prev - 1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateStep3()) {
      return;
    }

    setIsLoading(true);

    try {
      // TODO: 실제 API 호출로 교체
      // const response = await fetch('/api/auth/register', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify({
      //     name: formData.name,
      //     email: formData.email,
      //     password: formData.password,
      //     role: formData.role,
      //     introduction: formData.introduction
      //   }),
      // });

      // 임시 회원가입 로직 (데모용)
      await new Promise(resolve => setTimeout(resolve, 2000)); // 로딩 시뮬레이션

      // 회원가입 성공 시 자동 로그인
      const userData = {
        id: Date.now(),
        name: formData.name,
        email: formData.email,
        role: formData.role,
        introduction: formData.introduction,
        isApproved: formData.role === 'author' ? false : true // 작가는 승인 대기
      };

      localStorage.setItem('user', JSON.stringify(userData));
      localStorage.setItem('token', 'demo_token_' + Date.now());

      // 가입 완료 알림
      alert(
        formData.role === 'author' 
          ? '작가 회원가입이 완료되었습니다. 관리자 승인 후 서비스를 이용하실 수 있습니다.'
          : '회원가입이 완료되었습니다. 환영합니다!'
      );

      // 역할에 따른 리다이렉트
      switch (formData.role) {
        case 'author':
          navigate('/author/profile');
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
        submit: error.message || '회원가입 중 오류가 발생했습니다.'
      });
    } finally {
      setIsLoading(false);
    }
  };

  const renderStep1 = () => (
    <div className={styles.stepContent}>
      <h2 className={styles.stepTitle}>가입 유형 선택</h2>
      <p className={styles.stepSubtitle}>어떤 유형으로 가입하시겠습니까?</p>
      
      <div className={styles.roleSelection}>
        <div 
          className={`${styles.roleCard} ${formData.role === 'customer' ? styles.selected : ''}`}
          onClick={() => handleChange({ target: { name: 'role', value: 'customer' } })}
        >
          <div className={styles.roleIcon}>
            <span className="material-icons">person</span>
          </div>
          <h3>고객</h3>
          <p>도서를 구매하고 읽으실 수 있습니다</p>
          <ul>
            <li>도서 구매 및 다운로드</li>
            <li>구독 서비스 이용</li>
            <li>포인트 적립 및 사용</li>
            <li>개인 서재 관리</li>
          </ul>
        </div>
        
        <div 
          className={`${styles.roleCard} ${formData.role === 'author' ? styles.selected : ''}`}
          onClick={() => handleChange({ target: { name: 'role', value: 'author' } })}
        >
          <div className={styles.roleIcon}>
            <span className="material-icons">edit</span>
          </div>
          <h3>작가</h3>
          <p>원고를 작성하고 출판하실 수 있습니다</p>
          <ul>
            <li>원고 작성 및 관리</li>
            <li>AI 기반 도서 생성</li>
            <li>출판 현황 확인</li>
            <li>수익 관리</li>
          </ul>
        </div>
      </div>
      
      {errors.role && <span className={styles.errorMessage}>{errors.role}</span>}
    </div>
  );

  const renderStep2 = () => (
    <div className={styles.stepContent}>
      <h2 className={styles.stepTitle}>기본 정보 입력</h2>
      <p className={styles.stepSubtitle}>
        {formData.role === 'author' ? '작가' : '고객'} 정보를 입력해주세요
      </p>
      
      <div className={styles.formGrid}>
        <div className={styles.formGroup}>
          <label htmlFor="name" className={styles.formLabel}>
            <span className="material-icons">badge</span>
            이름
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className={`${styles.formInput} ${errors.name ? styles.error : ''}`}
            placeholder="실명을 입력하세요"
          />
          {errors.name && <span className={styles.errorMessage}>{errors.name}</span>}
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
            placeholder="8자 이상, 영문+숫자 포함"
          />
          {errors.password && <span className={styles.errorMessage}>{errors.password}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword" className={styles.formLabel}>
            <span className="material-icons">lock_outline</span>
            비밀번호 확인
          </label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            className={`${styles.formInput} ${errors.confirmPassword ? styles.error : ''}`}
            placeholder="비밀번호를 다시 입력하세요"
          />
          {errors.confirmPassword && <span className={styles.errorMessage}>{errors.confirmPassword}</span>}
        </div>

        {formData.role === 'author' && (
          <div className={`${styles.formGroup} ${styles.fullWidth}`}>
            <label htmlFor="introduction" className={styles.formLabel}>
              <span className="material-icons">description</span>
              작가 소개
            </label>
            <textarea
              id="introduction"
              name="introduction"
              value={formData.introduction}
              onChange={handleChange}
              className={`${styles.formInput} ${errors.introduction ? styles.error : ''}`}
              placeholder="간단한 자기소개와 작품 스타일을 작성해주세요"
              rows="4"
            />
            {errors.introduction && <span className={styles.errorMessage}>{errors.introduction}</span>}
          </div>
        )}
      </div>
    </div>
  );

  const renderStep3 = () => (
    <div className={styles.stepContent}>
      <h2 className={styles.stepTitle}>약관 동의</h2>
      <p className={styles.stepSubtitle}>서비스 이용을 위한 약관에 동의해주세요</p>
      
      <div className={styles.termsSection}>
        <div className={styles.termsItem}>
          <label className={styles.checkboxLabel}>
            <input
              type="checkbox"
              name="agreeTerms"
              checked={formData.agreeTerms}
              onChange={handleChange}
              className={styles.checkboxInput}
            />
            <span className={styles.checkboxCustom}></span>
            <span className={styles.checkboxText}>
              <strong>[필수]</strong> 이용약관에 동의합니다
            </span>
          </label>
          <div className={styles.termsContent}>
            <p>KT Library 서비스 이용약관의 주요 내용:</p>
            <ul>
              <li>서비스 이용에 관한 기본적인 규칙</li>
              <li>회원의 권리와 의무</li>
              <li>콘텐츠 이용 및 저작권 관련 사항</li>
              <li>서비스 제공 및 변경에 관한 사항</li>
            </ul>
          </div>
          {errors.agreeTerms && <span className={styles.errorMessage}>{errors.agreeTerms}</span>}
        </div>

        <div className={styles.termsItem}>
          <label className={styles.checkboxLabel}>
            <input
              type="checkbox"
              name="agreePrivacy"
              checked={formData.agreePrivacy}
              onChange={handleChange}
              className={styles.checkboxInput}
            />
            <span className={styles.checkboxCustom}></span>
            <span className={styles.checkboxText}>
              <strong>[필수]</strong> 개인정보 처리방침에 동의합니다
            </span>
          </label>
          <div className={styles.termsContent}>
            <p>개인정보 처리방침의 주요 내용:</p>
            <ul>
              <li>수집하는 개인정보의 항목</li>
              <li>개인정보의 수집 및 이용 목적</li>
              <li>개인정보의 보유 및 이용 기간</li>
              <li>개인정보의 제3자 제공에 관한 사항</li>
            </ul>
          </div>
          {errors.agreePrivacy && <span className={styles.errorMessage}>{errors.agreePrivacy}</span>}
        </div>
      </div>

      {errors.submit && (
        <div className={`${styles.errorMessage} ${styles.submitError}`}>
          <span className="material-icons">error</span>
          {errors.submit}
        </div>
      )}
    </div>
  );

  return (
    <div className={styles.registerContainer}>
      <div className={styles.registerCard}>
        <div className={styles.registerHeader}>
          <div className={styles.registerLogo}>
            <span className="material-icons">menu_book</span>
            <h1>KT Library</h1>
          </div>
          
          <div className={styles.stepIndicator}>
            {[1, 2, 3].map(step => (
              <div key={step} className={`${styles.step} ${currentStep >= step ? styles.active : ''}`}>
                <div className={styles.stepNumber}>{step}</div>
                <div className={styles.stepLabel}>
                  {step === 1 ? '유형선택' : step === 2 ? '정보입력' : '약관동의'}
                </div>
              </div>
            ))}
          </div>
        </div>

        <form className={styles.registerForm} onSubmit={handleSubmit}>
          {currentStep === 1 && renderStep1()}
          {currentStep === 2 && renderStep2()}
          {currentStep === 3 && renderStep3()}

          <div className={styles.formActions}>
            {currentStep > 1 && (
              <button 
                type="button" 
                className={styles.btnSecondary}
                onClick={handlePrevious}
              >
                <span className="material-icons">arrow_back</span>
                이전
              </button>
            )}
            
            {currentStep < 3 ? (
              <button 
                type="button" 
                className={styles.btnPrimary}
                onClick={handleNext}
              >
                다음
                <span className="material-icons">arrow_forward</span>
              </button>
            ) : (
              <button 
                type="submit" 
                className={`${styles.btnPrimary} ${isLoading ? styles.loading : ''}`}
                disabled={isLoading}
              >
                {isLoading ? (
                  <>
                    <span className={styles.loadingSpinner}></span>
                    회원가입 중...
                  </>
                ) : (
                  <>
                    <span className="material-icons">person_add</span>
                    회원가입 완료
                  </>
                )}
              </button>
            )}
          </div>
        </form>

        <div className={styles.registerFooter}>
          <p>
            이미 계정이 있으신가요?{' '}
            <Link to="/login" className={styles.loginLink}>
              로그인
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Register;