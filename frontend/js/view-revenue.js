document.addEventListener('DOMContentLoaded', () => {
    fetchRevenue();
});

async function fetchRevenue() {
    const token = localStorage.getItem('jwtToken');
    const response = await fetch('http://localhost:8085/admin/revenue', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    const revenue = await response.json();
    document.getElementById('dailyRevenue').textContent = `${revenue.revenueForCurrentDay} ${revenue.currency}`;
    document.getElementById('monthlyRevenue').textContent = `${revenue.revenueForCurrentMonth} ${revenue.currency}`;
    document.getElementById('yearlyRevenue').textContent = `${revenue.revenueForCurrentYear} ${revenue.currency}`;
}