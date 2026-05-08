// const JSON_FILE = "../json/19720494004-0539969009.json";
const JSON_FILE = "../json/12956329004-0813921060.json";

const fmt = (v) =>
   typeof v === "number"
      ? v.toLocaleString("fr-BE", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
      : (v ?? "");


function setSummary(data, attests, invoiceNotifications, paymentsFlat) {
   const summary = document.getElementById("summary");
   summary.innerHTML = `
        <div class="card"><b>idf</b>${data.idf ?? ""}</div>
        <div class="card"><b>nihdiNumber</b>${data.nihdiNumber ?? ""}</div>
        <div class="card"><b>enterpriseNumber</b>${data.enterpriseNumber ?? ""}</div>
        <div class="card"><b>persIdf</b>${data.persIdf ?? "null"}</div>
        <div class="card"><b>EAttests count</b>${attests.length}</div>


        <div class="card"><b>Invoice notifications count</b>${invoiceNotifications.length}</div>
        <div class="card"><b>Payment rows (flatten)</b>${paymentsFlat.length}</div>
      `;
}

function renderAttests(attests, year = "") {
   const body = document.getElementById("attestsBody");
   const rows = attests
      .filter(a => !year || String(a.niyear) === String(year))
      .sort((a, b) => (a.niyear - b.niyear) || (a.nimonth - b.nimonth) || (a.niidx - b.niidx))
      .map(a => `
          <tr>
            <td>${a.niyear ?? ""}</td>
            <td>${a.nimonth ?? ""}</td>
            <td>${a.niidx ?? ""}</td>
            <td class="right">${fmt(a.nitotalAmount)}</td>
            <td class="right">${fmt(a.nitotalCancelledAmount)}</td>
            <td class="right">${fmt(a.nitotalSupAmount)}</td>
            <td class="right">${fmt(a.nitotalSupCancelledAmount)}</td>
          </tr>
        `).join("");
   body.innerHTML = rows;
}




function renderInvoiceNotifications(invoiceNotifications = []) {
   const body = document.getElementById("invoiceNotificationsBody");
   body.innerHTML = invoiceNotifications.map(n => `
        <tr>
          <td>${n.idf ?? ""}</td>
          <td>${n.invoiceYear ?? ""}</td>
          <td>${n.invoiceMonth ?? ""}</td>
          <td class="right">${(n.invoiceInformations || []).length}</td>
        </tr>
      `).join("");
}


function flattenPayments(invoiceNotifications = []) {
   const out = [];
   for (const notif of invoiceNotifications) {
      for (const info of (notif.invoiceInformations || [])) {

         for (const p of (info.paymentInformations || [])) {
            out.push({
               notifId: notif.idf,
               invoiceYear: notif.invoiceYear,
               invoiceMonth: notif.invoiceMonth,
               infoId: info.idf,
               paymentDate: p.date,
               paymentAmount: p.amount || 0
            });
         }
      }
   }
   return out;
}

function renderPayments(paymentsFlat = []) {
   const body = document.getElementById("paymentsBody");
   body.innerHTML = paymentsFlat.map(r => `
        <tr>
          <td>${r.notifId ?? ""}</td>
          <td>${r.invoiceYear ?? ""}</td>
          <td>${r.invoiceMonth ?? ""}</td>
          <td>${r.infoId ?? ""}</td>
          <td>${r.paymentDate ?? ""}</td>
          <td class="right">${fmt(r.paymentAmount)}</td>
        </tr>
      `).join("");
}

async function init() {
   try {
      const res = await fetch(JSON_FILE);
      if (!res.ok) throw new Error(`HTTP ${res.status} sur ${JSON_FILE}`);
      const data = await res.json();

      // Le JSON contient EAttests et eattests (doublon probable) -> on priorise EAttests
      const attests = data.EAttests || data.eattests || [];

      const invoiceNotifications = data.invoiceNotifications || [];
      const paymentsFlat = flattenPayments(invoiceNotifications);


      setSummary(data, attests, invoiceNotifications, paymentsFlat);
      renderAttests(attests);

      renderInvoiceNotifications(invoiceNotifications);
      renderPayments(paymentsFlat);

      // Build filtre années
      const years = [...new Set(attests.map(a => a.niyear).filter(Boolean))].sort((a, b) => a - b);
      const yearFilter = document.getElementById("yearFilter");
      years.forEach(y => {
         const opt = document.createElement("option");
         opt.value = y;
         opt.textContent = y;
         yearFilter.appendChild(opt);
      });
      yearFilter.addEventListener("change", (e) => renderAttests(attests, e.target.value));

   } catch (err) {
      document.getElementById("error").textContent =
         `Erreur de chargement: ${err.message}. Lance un serveur local (pas file://).`;
   }
}

