import { useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';
import { getCurrentUser, login, logout } from './auth';

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    let active = true;

    getCurrentUser()
      .then((profile) => {
        if (active) {
          setUser(profile);
        }
      })
      .catch(() => {
        if (active) {
          logout();
          setUser(null);
        }
      });

    return () => {
      active = false;
    };
  }, []);

  const handleLogin = async (username, password) => {
    await login(username, password);
    const profile = await getCurrentUser();
    setUser(profile);
    return profile;
  };

  const handleLogout = () => {
    logout();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login: handleLogin, logout: handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
}
