// middleware.ts
import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
  const token = request.cookies.get('auth_token');
  const { pathname } = request.nextUrl;

  // Si intenta ir a rutas protegidas sin estar logueado
  if ((pathname.startsWith('/dashboard') || pathname.startsWith('/presupuestos')) && !token) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Si ya está logueado y trata de ir al login, lo mandamos al dashboard
  if (pathname.startsWith('/login') && token) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/dashboard/:path*', '/presupuestos/:path*', '/login'],
};