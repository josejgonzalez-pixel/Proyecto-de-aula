'use client';
import { createContext, useContext, useState, useEffect } from 'react';

interface AuthContextType {
    userId: string;
    setUserId: (id: string) => void;
}

const AuthContext = createContext<AuthContextType>({ userId: '0', setUserId: () => {} });

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    // Inicializamos el estado intentando leer del localStorage
    const [userId, setUserId] = useState<string>(() => {
        if (typeof window !== 'undefined') {
            return localStorage.getItem('userId') || '0';
        }
        return '0';
    });

    // Cada vez que userId cambie, lo guardamos en localStorage
    useEffect(() => {
        if (userId !== '0') {
            localStorage.setItem('userId', userId);
        } else {
            localStorage.removeItem('userId');
        }
    }, [userId]);

    return (
        <AuthContext.Provider value={{ userId, setUserId }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);