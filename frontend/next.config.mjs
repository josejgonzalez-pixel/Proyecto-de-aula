/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*', // Cuando vayas a /api en el frontend
        destination: 'http://localhost:8080/FinanziApp/:path*', // Se redirige al backend Java
      },
    ];
  },
};

export default nextConfig;