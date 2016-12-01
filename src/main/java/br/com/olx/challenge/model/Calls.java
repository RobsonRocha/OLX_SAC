package br.com.olx.challenge.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries(value = { @NamedQuery(name = "Calls.allCalls",
  query = "SELECT c FROM Calls c order by c.insertionDate desc"),
  @NamedQuery(name="Calls.callsByLogin", query="select c from Calls c "
  + "WHERE c.user.login = :login order by c.insertionDate desc"),
  @NamedQuery(name = "Calls.allGroups",
  query = "SELECT distinct to_char(c.insertionDate,'dd/mm/yyyy'), c.state, c.insertionDate FROM Calls c order by c.insertionDate desc"),
  @NamedQuery(name = "Calls.allGroupsByLogin",
  query = "SELECT distinct to_char(c.insertionDate,'dd/mm/yyyy'), c.state, c.insertionDate FROM Calls c where c.user.login = :login order by c.insertionDate desc")})
@Table(name = "atendimentos", schema="olx_sac")
public class Calls {
	
	private static final String REASON_DOUBT = "Dúvida"; 
	private static final String REASON_PRAISE = "Elogio";
	private static final String REASON_SUGGESTION = "Sugestão";
	
	private static final String TYPE_CALL_PHONE = "Telefone"; 
	private static final String TYPE_CALL_CHAT = "Chat";
	private static final String TYPE_CALL_EMAIL = "E-mail";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="numero")
	private Long number;
	
	@Column(name="tipo_chamado")
	private char typeCall;
	
	@Column(name="uf")
	private String state;
	
	@Column(name="motivo")
	private char reason;
	
	@Column(name="descricao")
	private String description;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="login")
    private UserLogin user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_insercao")
	private Date insertionDate;
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public char getTypeCall() {
		return typeCall;
	}
	
	public String getTypeCallString() {
		
		switch (this.typeCall){
			case 'E':
				return TYPE_CALL_EMAIL;
			case 'T':
				return TYPE_CALL_PHONE;
			case 'C':
				return TYPE_CALL_CHAT;
			default:
				return "";
		};		
	}

	public void setTypeCall(char typeCall) {
		this.typeCall = typeCall;
	}

	public String getState() {
		if(state == null)
			return null;
		return state.toUpperCase();
	}

	public void setState(String state) {
		this.state = state;
	}

	public char getReason() {
		return reason;
	}
	
	public String getReasonString() {
		switch(this.reason){ 
			case 'D':
				return REASON_DOUBT;
			case 'E':
				return REASON_PRAISE;
			case 'S':
				return REASON_SUGGESTION;
			default:
				return "";
		};
	}

	public void setReason(char reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserLogin getUser() {
		return user;
	}

	public void setUser(UserLogin user) {
		this.user = user;
	}

	public Date getInsertionDate() {
		
		return this.insertionDate;
	}
	
	public String getInsertionDateString() {
		String date = new SimpleDateFormat("dd/MM/yyyy").format(this.insertionDate);
		return date;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}
	
	@Override
    public int hashCode() {
              final int prime = 31;
              int result = 1;
              result = prime * result + ((number == null) ? 0 :
              number.hashCode());
              return result;
    }

    @Override
    public boolean equals(Object obj) {
              if (this == obj)
                       return true;
              if (obj == null)
                       return false;
              if (getClass() != obj.getClass())
                       return false;
              
              return (obj instanceof Calls) ? 
                (this.getNumber() == null ? this == obj :
                this.getNumber().equals((
                 (Calls)obj).getNumber())):false;
    }        
	
}
